package com.Group3.Travels.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(optional=false)
    @JoinColumn(name="destination_id")
    private Destination destination;

    private String hotelName;
    private LocalDate departureDate;
    private Integer weeks;

    private Double totalPriceSek;
    private Double totalPricePln;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Booking() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getWeeks() {
        return weeks;
    }

    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }

    public Double getTotalPriceSek() {
        return totalPriceSek;
    }

    public void setTotalPriceSek(Double totalPriceSek) {
        this.totalPriceSek = totalPriceSek;
    }

    public Double getTotalPricePln() {
        return totalPricePln;
    }

    public void setTotalPricePln(Double totalPricePln) {
        this.totalPricePln = totalPricePln;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
