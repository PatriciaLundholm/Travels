package com.Group3.Travels.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<com.group3.travels.entity.Address, Long> {
}
