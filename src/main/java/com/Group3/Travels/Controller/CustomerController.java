package com.Group3.Travels.Controller;

import com.Group3.Travels.Entity.Customer;
import com.Group3.Travels.Repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer c) {
        Customer saved = customerRepository.save(c);
        URI location = URI.create("/api/v1/customers/" + saved.getId());
        System.out.println("Admin created customer " + saved.getUsername());
        return ResponseEntity.created(location).body(saved);
    }


    @PutMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody Customer c) {
        return customerRepository.findById(customerId)
                .map(existing -> {
                    existing.setFullName(c.getFullName());
                    existing.setEmail(c.getEmail());
                    existing.setPhone(c.getPhone());
                    Customer updated = customerRepository.save(existing);
                    System.out.println("Admin updated customer " + existing.getUsername());
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            return ResponseEntity.notFound().build();
        }
        customerRepository.deleteById(customerId);
        System.out.println("Admin deleted customer with id " + customerId);
        return ResponseEntity.noContent().build();
    }
}
