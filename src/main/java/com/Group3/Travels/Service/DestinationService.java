package com.Group3.Travels.Service;


import com.Group3.Travels.Repository.DestinationRepository;
import org.springframework.stereotype.Service;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;
    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }
}
