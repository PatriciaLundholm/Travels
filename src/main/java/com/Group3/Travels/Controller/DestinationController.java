package com.Group3.Travels.Controller;

import com.Group3.Travels.Entity.Destination;
import com.Group3.Travels.Repository.DestinationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public List<Destination> listAll() {
        return repo.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Destination> create(@RequestBody Destination d) {
        Destination saved = repo.save(d);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.getId()).toUri();
        System.out.println("admin created destination " + saved.getCity());
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Destination> update(@PathVariable Long id, @RequestBody Destination d) {
        return repo.findById(id).map(existing -> {
            existing.setCity(d.getCity());
            existing.setCountry(d.getCountry());
            existing.setHotelName(d.getHotelName());
            existing.setPricePerWeekSek(d.getPricePerWeekSek());
            existing.setPricePerWeekPln(d.getPricePerWeekPln());
            repo.save(existing);
            System.out.println("admin updated destination " + existing.getCity());
            return ResponseEntity.ok(existing);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            System.out.println("admin deleted destination id=" + id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
