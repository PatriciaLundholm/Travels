package com.Group3.Travels.Controller;

import com.Group3.Travels.Entity.*;
import com.Group3.Travels.Repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingRepository bookingRepo;
    private final CustomerRepository customerRepo;
    private final DestinationRepository destinationRepo;

    public BookingController(BookingRepository bookingRepo, CustomerRepository customerRepo, DestinationRepository destinationRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.destinationRepo = destinationRepo;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> createBooking(@RequestBody Booking b) {
        var customer = customerRepo.findById(b.getCustomer().getId()).orElse(null);
        var dest = destinationRepo.findById(b.getDestination().getId()).orElse(null);
        if (customer == null || dest == null) return ResponseEntity.badRequest().body("Invalid customer or destination");

        b.setHotelName(dest.getHotelName());
        b.setTotalPriceSek(dest.getPricePerWeekSek() * b.getWeeks());
        b.setTotalPricePln(dest.getPricePerWeekPln() * b.getWeeks());
        Booking saved = bookingRepo.save(b);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        System.out.println("customer " + customer.getUsername() + " booked trip to " + dest.getCity());
        return ResponseEntity.created(location).body(saved);
    }

    @PatchMapping("/{bookingId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> patchBooking(@PathVariable Long bookingId, @RequestBody Booking patch) {
        return bookingRepo.findById(bookingId).map(b -> {
            if (patch.getWeeks() != null) {
                b.setWeeks(patch.getWeeks());
                b.setTotalPriceSek(b.getDestination().getPricePerWeekSek() * b.getWeeks());
                b.setTotalPricePln(b.getDestination().getPricePerWeekPln() * b.getWeeks());
            }
            if (patch.getDestination() != null && patch.getDestination().getId() != null) {
                destinationRepo.findById(patch.getDestination().getId()).ifPresent(dest -> {
                    b.setDestination(dest);
                    b.setHotelName(dest.getHotelName());
                    b.setTotalPriceSek(dest.getPricePerWeekSek() * b.getWeeks());
                    b.setTotalPricePln(dest.getPricePerWeekPln() * b.getWeeks());
                });
            }
            if (patch.getHotelName() != null) b.setHotelName(patch.getHotelName());
            bookingRepo.save(b);
            System.out.println("booking " + bookingId + " updated");
            return ResponseEntity.ok(b);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Booking>> getBookingsByCustomer(@RequestParam Long customerId) {
        var list = bookingRepo.findByCustomerId(customerId);
        return ResponseEntity.ok(list);
    }
}
