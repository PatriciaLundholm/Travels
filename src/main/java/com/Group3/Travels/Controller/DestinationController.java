package com.Group3.Travels.Controller;

import com.Group3.Travels.Entity.Destination;
import com.Group3.Travels.Repository.DestinationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/destinations")
public class DestinationController {

    private final DestinationRepository repo;
    public DestinationController(DestinationRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Destination> getAll() {
        return repo.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Destination> add(@RequestBody Destination d) {
        Destination saved = repo.save(d);
        URI location = URI.create("/api/v1/destinations/" + saved.getId());
        System.out.println("Admin created destination " + saved.getCity());
        return ResponseEntity.created(location).body(saved);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Destination> update(@PathVariable Long id, @RequestBody Destination newData) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setCity(newData.getCity());
                    existing.setCountry(newData.getCountry());
                    existing.setHotelName(newData.getHotelName());
                    existing.setPricePerWeekSek(newData.getPricePerWeekSek());
                    existing.setPricePerWeekPln(newData.getPricePerWeekSek() * 0.4);
                    Destination updated = repo.save(existing);
                    System.out.println("Admin updated destination " + existing.getCity());
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        System.out.println("Admin deleted destination with id " + id);
        return ResponseEntity.noContent().build();
    }
}
