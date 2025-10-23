package com.Group3.Travels.Bootstrap;



import com.Group3.Travels.Entity.Customer;
import com.Group3.Travels.Entity.Destination;
import com.Group3.Travels.Repository.CustomerRepository;
import com.Group3.Travels.Repository.DestinationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initData(CustomerRepository custRepo, DestinationRepository destRepo) {
        return args -> {

            if (custRepo.count() == 0) {
                custRepo.save(new Customer("anna02", "Anna Lundholm", "anna@wigell.com", "0701234569"));
                custRepo.save(new Customer("bjorn88", "Björn Karlsson", "bjorn@wigell.se", "0702345678"));
                custRepo.save(new Customer("carina77", "Carina Svensson", "carina@wigell.se", "0703456789"));
                custRepo.save(new Customer("daniel99", "Daniel Andersson", "daniel@wigell.se", "0704567890"));
                custRepo.save(new Customer("elin22", "Elin Johansson", "elin@wigell.se", "0705678901"));
            }

            if (destRepo.count() == 0) {
                destRepo.save(new Destination("Stockholm", "Sweden", "Grand Hotel", 12000.0, 4800.0));
                destRepo.save(new Destination("Warsaw", "Poland", "Hotel Bristol", 7000.0, 2800.0));
                destRepo.save(new Destination("Paris", "France", "Hotel Le Meurice", 15000.0, 6000.0));
                destRepo.save(new Destination("Rome", "Italy", "Hotel Eden", 13000.0, 5200.0));
                destRepo.save(new Destination("London", "UK", "The Savoy", 14000.0, 5600.0));
            }
        };
    }
}
