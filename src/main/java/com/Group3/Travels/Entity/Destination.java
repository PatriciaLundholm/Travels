package com.Group3.Travels.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "destination")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hotelName;
    private String city;
    private String country;

    @Column(name = "price_per_week_sek")
    private Double pricePerWeekSek;

    @Column(name = "price_per_week_pln")
    private Double pricePerWeekPln;


    public Destination() {}


    public Destination(String city, String country, String hotelName, Double pricePerWeekSek, Double pricePerWeekPln) {
        this.city = city;
        this.country = country;
        this.hotelName = hotelName;
        this.pricePerWeekSek = pricePerWeekSek;
        this.pricePerWeekPln = pricePerWeekPln;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
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

    public Double getPricePerWeekSek() {
        return pricePerWeekSek;
    }

    public void setPricePerWeekSek(Double pricePerWeekSek) {
        this.pricePerWeekSek = pricePerWeekSek;
    }

    public Double getPricePerWeekPln() {
        return pricePerWeekPln;
    }

    public void setPricePerWeekPln(Double pricePerWeekPln) {
        this.pricePerWeekPln = pricePerWeekPln;
    }
}
