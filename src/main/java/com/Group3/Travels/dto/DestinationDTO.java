package com.Group3.Travels.dto;

public class DestinationDTO {

    private String hotel_name;
    private String city;
    private String country;
    private double pricePerWeekSek;
    private double pricePerWeekPln;

    public DestinationDTO() {}

    public String getHotel_name() {
        return hotel_name;
    }
    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public double getPricePerWeekSek() {
        return pricePerWeekSek;
    }
    public void setPricePerWeekSek(double pricePerWeekSek) {
        this.pricePerWeekSek = pricePerWeekSek;
    }
    public double getPricePerWeekPln() {
        return pricePerWeekPln;
    }
    public void setPricePerWeekPln(double pricePerWeekPln) {
        this.pricePerWeekPln = pricePerWeekPln;
    }
}
