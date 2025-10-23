package com.Group3.Travels.Bootstrap;

import com.Group3.Travels.Entity.Booking;
import com.Group3.Travels.Entity.Customer;
import com.Group3.Travels.Entity.Destination;
import com.Group3.Travels.Repository.BookingRepository;
import com.Group3.Travels.Repository.CustomerRepository;
import com.Group3.Travels.Repository.DestinationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class BookingSeeder {

    private static final double SEK_TO_PLN = 0.4; // Fast växelkurs, 1 SEK = 0.4 PLN

    @Bean
    CommandLineRunner seedBookings(
            BookingRepository bookingRepo,
            CustomerRepository customerRepo,
            DestinationRepository destinationRepo
    ) {
        return args -> {

            if (bookingRepo.count() == 0) {

                List<Customer> customers = customerRepo.findAll();
                List<Destination> destinations = destinationRepo.findAll();

                if(customers.isEmpty() || destinations.isEmpty()){
                    System.out.println("Inga kunder eller destinationer att skapa bokningar för!");
                    return;
                }

                // Skapa några bokningar med automatisk valutakonvertering
                createBooking(bookingRepo, customers.get(0), destinations.get(0), 2, LocalDate.now().plusWeeks(1));
                createBooking(bookingRepo, customers.get(1), destinations.get(1), 1, LocalDate.now().plusWeeks(2));
                createBooking(bookingRepo, customers.get(2), destinations.get(2), 3, LocalDate.now().plusWeeks(3));
            }
        };
    }

    private void createBooking(BookingRepository bookingRepo, Customer customer, Destination destination, int weeks, LocalDate departureDate) {
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setDestination(destination);
        booking.setWeeks(weeks);
        booking.setDepartureDate(departureDate);
        booking.setHotelName(destination.getHotelName());
        booking.setTotalPriceSek(destination.getPricePerWeekSek() * weeks);
        booking.setTotalPricePln(destination.getPricePerWeekSek() * weeks * SEK_TO_PLN); // Omräkning från SEK
        bookingRepo.save(booking);
        System.out.println("Booking created: " + customer.getUsername() + " -> " + destination.getCity() + " | SEK: " + booking.getTotalPriceSek() + ", PLN: " + booking.getTotalPricePln());
    }
}

