package com.Group3.Travels.Controller;

import com.Group3.Travels.dto.BookingDTO;
import com.Group3.Travels.Entity.Booking;
import com.Group3.Travels.Entity.Customer;
import com.Group3.Travels.Entity.Destination;
import com.Group3.Travels.Repository.BookingRepository;
import com.Group3.Travels.Repository.CustomerRepository;
import com.Group3.Travels.Repository.DestinationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingRepository bookingRepo;
    private final CustomerRepository customerRepo;
    private final DestinationRepository destinationRepo;

    private static final double SEK_TO_PLN = 0.45; // fast växelkurs

    public BookingController(BookingRepository bookingRepo,
                             CustomerRepository customerRepo,
                             DestinationRepository destinationRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.destinationRepo = destinationRepo;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO dto) {
        Customer customer = customerRepo.findById(dto.getCustomerId()).orElse(null);
        Destination destination = destinationRepo.findById(dto.getDestinationId()).orElse(null);

        if (customer == null) {
            return ResponseEntity.badRequest().body("Customer with ID " + dto.getCustomerId() + " not found");
        }
        if (destination == null) {
            return ResponseEntity.badRequest().body("Destination with ID " + dto.getDestinationId() + " not found");
        }

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setDestination(destination);
        booking.setHotelName(dto.getHotelName() != null ? dto.getHotelName() : destination.getHotelName());
        booking.setDepartureDate(dto.getDepartureDate());
        booking.setWeeks(dto.getWeeks());

        double totalSek = destination.getPricePerWeekSek() * dto.getWeeks();
        double totalPln = totalSek * SEK_TO_PLN;

        booking.setTotalPriceSek(totalSek);
        booking.setTotalPricePln(totalPln);

        Booking saved = bookingRepo.save(booking);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        System.out.println("Customer " + customer.getUsername() +
                " booked trip to " + destination.getCity() +
                " for " + totalSek + " SEK / " + totalPln + " PLN");

        return ResponseEntity.created(location).body(saved);
    }


    @PatchMapping("/{bookingId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> patchBooking(@PathVariable Long bookingId, @RequestBody BookingDTO dto) {
        return bookingRepo.findById(bookingId).map(b -> {

            if (dto.getWeeks() != null) {
                b.setWeeks(dto.getWeeks());
                b.setTotalPriceSek(b.getDestination().getPricePerWeekSek() * b.getWeeks());
                b.setTotalPricePln(b.getTotalPriceSek() * SEK_TO_PLN);
            }

            if (dto.getDestinationId() != null) {
                destinationRepo.findById(dto.getDestinationId()).ifPresent(dest -> {
                    b.setDestination(dest);
                    b.setHotelName(dto.getHotelName() != null ? dto.getHotelName() : dest.getHotelName());
                    b.setTotalPriceSek(dest.getPricePerWeekSek() * b.getWeeks());
                    b.setTotalPricePln(b.getTotalPriceSek() * SEK_TO_PLN);
                });
            }

            if (dto.getHotelName() != null) {
                b.setHotelName(dto.getHotelName());
            }

            if (dto.getDepartureDate() != null) {
                b.setDepartureDate(dto.getDepartureDate());
            }

            bookingRepo.save(b);
            System.out.println("Booking " + bookingId + " updated: " +
                    b.getTotalPriceSek() + " SEK / " + b.getTotalPricePln() + " PLN");
            return ResponseEntity.ok(b);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Booking>> getBookingsByCustomer(@RequestParam Long customerId) {
        List<Booking> list = bookingRepo.findByCustomerId(customerId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }
}
