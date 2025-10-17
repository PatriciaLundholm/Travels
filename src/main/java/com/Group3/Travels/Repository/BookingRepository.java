package com.Group3.Travels.Repository;

import com.Group3.Travels.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findByCustomerId(Long customerId);
}

