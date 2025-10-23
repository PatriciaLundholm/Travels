package com.Group3.Travels.Controller;

import com.Group3.Travels.Entity.Address;
import com.Group3.Travels.Entity.Customer;
import com.Group3.Travels.Repository.AddressRepository;
import com.Group3.Travels.Repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/addresses")
public class AddressController {

    private final CustomerRepository customerRepo;
    private final AddressRepository addressRepo;

    public AddressController(CustomerRepository customerRepo, AddressRepository addressRepo) {
        this.customerRepo = customerRepo;
        this.addressRepo = addressRepo;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Address> addAddress(@PathVariable Long customerId, @RequestBody Address a) {
        Customer customer = customerRepo.findById(customerId).orElse(null);
        if (customer == null) {
            return ResponseEntity.badRequest().build();
        }

        a.setCustomer(customer);
        Address saved = addressRepo.save(a);
        URI location = URI.create("/api/v1/customers/" + customerId + "/addresses/" + saved.getId());
        System.out.println("Admin added address " + saved.getStreet() + " for customer " + customer.getUsername());
        return ResponseEntity.created(location).body(saved);
    }


    @DeleteMapping("/{addressId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long customerId, @PathVariable Long addressId) {
        Customer customer = customerRepo.findById(customerId).orElse(null);
        if (customer == null || !addressRepo.existsById(addressId)) {
            return ResponseEntity.notFound().build();
        }

        Address address = addressRepo.findById(addressId).get();
        if (!address.getCustomer().getId().equals(customerId)) {
            return ResponseEntity.status(403).build(); // Förhindrar borttagning av adress som inte tillhör kunden
        }

        addressRepo.deleteById(addressId);
        System.out.println("Admin deleted address " + addressId + " for customer " + customer.getUsername());
        return ResponseEntity.noContent().build();
    }
}

