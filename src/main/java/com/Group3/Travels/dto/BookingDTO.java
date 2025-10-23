package com.Group3.Travels.dto;

import java.time.LocalDate;

public class BookingDTO {

    private Long customerId;
    private Long destinationId;
    private String hotelName; // valfritt, om man vill skriva över standardhotellet
    private LocalDate departureDate;
    private Integer weeks;

    public BookingDTO() {}

    public Long getCustomerId() {
        return customerId;
    }

    public BookingDTO setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public BookingDTO setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
        return this;
    }

    public String getHotelName() {
        return hotelName;
    }

    public BookingDTO setHotelName(String hotelName) {
        this.hotelName = hotelName;
        return this;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public BookingDTO setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public Integer getWeeks() {
        return weeks;
    }

    public BookingDTO setWeeks(Integer weeks) {
        this.weeks = weeks;
        return this;
    }
}
