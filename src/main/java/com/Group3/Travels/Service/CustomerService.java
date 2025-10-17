package com.Group3.Travels.Service;

import com.Group3.Travels.Entity.Customer;
import com.Group3.Travels.Repository.AddressRepository;
import com.Group3.Travels.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        Customer saved = customerRepository.save(customer);
        System.out.println("admin created customer " + saved.getFullName());
        return saved;
    }

    public Optional<Customer> updateCustomer(Long id, Customer updated) {
        return customerRepository.findById(id).map(existing -> {
            existing.setFullName(updated.getFullName());
            existing.setEmail(updated.getEmail());
            existing.setPhone(updated.getPhone());
            System.out.println("admin updated customer " + existing.getFullName());
            return customerRepository.save(existing);
        });
    }

    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            System.out.println("admin deleted customer id=" + id);
            return true;
        }
        return false;
    }

    public Optional<com.group3.travels.entity.Address> addAddress(Long customerId, com.group3.travels.entity.Address address) {
        return customerRepository.findById(customerId).map(customer -> {
            address.setCustomer(customer);
            com.group3.travels.entity.Address saved = addressRepository.save(address);
            System.out.println("admin added address for customer id=" + customerId);
            return saved;
        });
    }

    public boolean deleteAddress(Long addressId) {
        if (addressRepository.existsById(addressId)) {
            addressRepository.deleteById(addressId);
            System.out.println("admin deleted address id=" + addressId);
            return true;
        }
        return false;
    }
}
