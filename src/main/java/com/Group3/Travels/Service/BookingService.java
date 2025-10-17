package com.Group3.Travels.Service;

import com.Group3.Travels.Entity.Booking;
import com.Group3.Travels.Entity.Customer;
import com.Group3.Travels.Entity.Destination;
import com.Group3.Travels.Repository.BookingRepository;
import com.Group3.Travels.Repository.CustomerRepository;
import com.Group3.Travels.Repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final DestinationRepository destinationRepository;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, DestinationRepository destinationRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.destinationRepository = destinationRepository;
    }

    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    public Optional<Booking> createBooking(Booking booking) {
        Optional<Customer> customerOpt = customerRepository.findById(booking.getCustomer().getId());
        Optional<Destination> destOpt = destinationRepository.findById(booking.getDestination().getId());

        if (customerOpt.isPresent() && destOpt.isPresent()) {
            Customer customer = customerOpt.get();
            Destination dest = destOpt.get();

            booking.setCustomer(customer);
            booking.setDestination(dest);
            booking.setHotelName(dest.getHotelName());
            booking.setTotalPriceSek(dest.getPricePerWeekSek() * booking.getWeeks());
            booking.setTotalPricePln(dest.getPricePerWeekPln() * booking.getWeeks());

            Booking saved = bookingRepository.save(booking);
            System.out.println("customer " + customer.getUsername() + " booked " + dest.getCity());
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    public Optional<Booking> updateBooking(Long bookingId, Booking patch) {
        return bookingRepository.findById(bookingId).map(b -> {
            if (patch.getWeeks() != null) {
                b.setWeeks(patch.getWeeks());
                b.setTotalPriceSek(b.getDestination().getPricePerWeekSek() * b.getWeeks());
                b.setTotalPricePln(b.getDestination().getPricePerWeekPln() * b.getWeeks());
            }
            if (patch.getDestination() != null && patch.getDestination().getId() != null) {
                destinationRepository.findById(patch.getDestination().getId()).ifPresent(dest -> {
                    b.setDestination(dest);
                    b.setHotelName(dest.getHotelName());
                    b.setTotalPriceSek(dest.getPricePerWeekSek() * b.getWeeks());
                    b.setTotalPricePln(dest.getPricePerWeekPln() * b.getWeeks());
                });
            }
            if (patch.getHotelName() != null) {
                b.setHotelName(patch.getHotelName());
            }
            System.out.println("booking " + bookingId + " updated");
            return bookingRepository.save(b);
        });
    }
}

