package com.Group3.Travels.Service;

import com.Group3.Travels.Entity.Destination;
import com.Group3.Travels.Repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Optional<Destination> getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }

    public Destination createDestination(Destination destination) {
        Destination saved = destinationRepository.save(destination);
        System.out.println("admin created destination " + saved.getCity());
        return saved;
    }

    public Optional<Destination> updateDestination(Long id, Destination updated) {
        return destinationRepository.findById(id).map(existing -> {
            existing.setCity(updated.getCity());
            existing.setCountry(updated.getCountry());
            existing.setHotelName(updated.getHotelName());
            existing.setPricePerWeekSek(updated.getPricePerWeekSek());
            existing.setPricePerWeekPln(updated.getPricePerWeekPln());
            System.out.println("admin updated destination " + existing.getCity());
            return destinationRepository.save(existing);
        });
    }

    public boolean deleteDestination(Long id) {
        if (destinationRepository.existsById(id)) {
            destinationRepository.deleteById(id);
            System.out.println("admin deleted destination id=" + id);
            return true;
        }
        return false;
    }
}

