package com.Group3.Travels.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "destination")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hotel_name;
    private String city;
    private String country;

    @Column(name = "price_per_week_sek")
    private Double pricePerWeekSek;

    @Column(name = "price_per_week_pln")
    private Double pricePerWeekPln;

    public Destination() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return hotel_name;
    }

    public void setName(String name) {
        this.hotel_name = name;
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
