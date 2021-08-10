package com.example.demo.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @Column(name = "VIN", nullable = false, length = 17)
    @NonNull
    private String vin;

    @Column(name = "make", nullable = false)
    @NonNull
    @NotEmpty(message = "'make' field was empty")
    private String make;

    @Column(name = "model", nullable = false)
    @NonNull
    @NotEmpty(message = "model' field was empty")
    private String model;

    @Column(name = "year", nullable = false)
    @NonNull
    // Fun fact: VINs were first used until 1954 in the United States
    @DecimalMin(value = "1954", message = "VINs before 1954 are not accepted")
    private Integer year;

    @Column(name = "is_older", nullable = true)
    private Boolean is_older;

    public Vehicle() {
    }

    public Vehicle(@NonNull String vin, @NonNull @NotEmpty(message = "'make' field was empty") String make, @NonNull @NotEmpty(message = "model' field was empty") String model, @NonNull @DecimalMin(value = "1954", message = "VINs before 1954 are not accepted") Integer year, Boolean is_older) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.is_older = is_older;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getIs_older() {
        return is_older;
    }

    public void setIs_older(Boolean is_older) {
        this.is_older = is_older;
    }
}
