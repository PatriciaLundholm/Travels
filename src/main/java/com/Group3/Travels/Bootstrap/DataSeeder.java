package com.group3.travels.bootstrap;

import com.Group3.Travels.Entity.Customer;
import com.Group3.Travels.Entity.Destination;
import com.Group3.Travels.Repository.CustomerRepository;
import com.Group3.Travels.Entity.*;
import com.Group3.Travels.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initData(CustomerRepository custRepo, DestinationRepository destRepo) {
        return args -> {
            if (custRepo.count() == 0) {
                Customer c1 = new Customer(); c1.setUsername("anna01"); c1.setFullName("Anna Wigell"); c1.setEmail("anna@ex.se");
                Customer c2 = new Customer(); c2.setUsername("bjorn88"); c2.setFullName("Björn Karlsson"); c2.setEmail("bjorn@ex.se");
                Customer c3 = new Customer(); c3.setUsername("carina77"); c3.setFullName("Carina Svensson"); c3.setEmail("carina@ex.se");
                Customer c4 = new Customer(); c4.setUsername("daniel99"); c4.setFullName("Daniel Andersson"); c4.setEmail("daniel@ex.se");
                Customer c5 = new Customer(); c5.setUsername("elin22"); c5.setFullName("Elin Johansson"); c5.setEmail("elin@ex.se");
                custRepo.saveAll(List.of(c1,c2,c3,c4,c5));
            }

            if (destRepo.count() == 0) {
                Destination d1 = new Destination(); d1.setCity("Stockholm"); d1.setCountry("Sweden"); d1.setHotelName("Grand Hotel"); d1.setPricePerWeekSek(12000.0); d1.setPricePerWeekPln(4800.0);
                Destination d2 = new Destination(); d2.setCity("Warsaw"); d2.setCountry("Poland"); d2.setHotelName("Hotel Bristol"); d2.setPricePerWeekSek(7000.0); d2.setPricePerWeekPln(2800.0);
                Destination d3 = new Destination(); d3.setCity("Paris"); d3.setCountry("France"); d3.setHotelName("Le Meurice"); d3.setPricePerWeekSek(15000.0); d3.setPricePerWeekPln(6000.0);
                Destination d4 = new Destination(); d4.setCity("Rome"); d4.setCountry("Italy"); d4.setHotelName("Hotel Eden"); d4.setPricePerWeekSek(13000.0); d4.setPricePerWeekPln(5200.0);
                Destination d5 = new Destination(); d5.setCity("London"); d5.setCountry("UK"); d5.setHotelName("The Savoy"); d5.setPricePerWeekSek(14000.0); d5.setPricePerWeekPln(5600.0);
                destRepo.saveAll(List.of(d1,d2,d3,d4,d5));
            }
        };
    }
}

