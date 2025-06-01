package org.example.carrentalbackend.vehicle;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.carrentalbackend.order.Order;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @Column(name = "vin_number", nullable = false, updatable = false)
    @JsonProperty("vinNumber")
    @NotBlank(message = "Vin number is blank")
    @Size(min = 17, max = 17, message = "Vin must be 17 characters long")
    private String vinNumber;

    @Column(name = "make", nullable = false)
    @JsonProperty("make")
    @NotBlank(message = "Vehicle Make is blank")
    private String make;

    @Column(name = "model", nullable = false)
    @JsonProperty("model")
    @NotBlank(message = "Vehicle Model is blank")
    private String model;

    @Column(name = "year", nullable = false)
    @JsonProperty("year")
    @NotNull(message = "Year is blank")
    @JsonSerialize(using = YearSerializer.class)
    private Year year;

    @Column(name = "color", nullable = false)
    @JsonProperty("color")
    @NotBlank(message = "Vehicle Color is blank")
    private String color;

    @Column(name = "license_plate", nullable = false)
    @JsonProperty("licensePlate")
    @NotBlank(message = "Vehicle License Plate is blank")
    private String licensePlate;

    @Column(name = "car_type", nullable = false)
    @JsonProperty("carType")
    @Enumerated(EnumType.STRING)
    private CarType carType;

    @Column(name = "current_mileage", nullable = false)
    @JsonProperty("currentMileage")
    @Min(value = 0)
    @NotNull(message = "Vehicle Current mileage is blank")
    private int currentMileage;

    @Column(name = "city_mpg", nullable = false)
    @JsonProperty("cityMpg")
    @Min(value = 0)
    @NotNull(message = "Vehicle City MPG is blank")
    private int cityMpg;

    @Column(name = "highway_mpg", nullable = false)
    @JsonProperty("highwayMpg")
    @Min(value = 0)
    @NotNull(message = "Vehicle Highway MPG is blank")
    private int highwayMpg;

    @Column(name = "is_available", nullable = false)
    @JsonProperty("isAvailable")
    @NotNull(message = "Vehicle Availability is blank")
    private Boolean isAvailable;

    @Column(name = "rate", nullable = false)
    @JsonProperty("rate")
    @Min(value = 0)
    @NotNull(message = "Vehicle Rental Rate is blank")
    private double rate;

    @Column(name = "vehicle_img_src", nullable = false)
    @JsonProperty("vehicleImgSrc")
    @NotBlank(message = "Vehicle Image source is blank")
    private String vehicleImgSrc;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "vehicle-order")
    @Valid
    private List<Order> orders;

    public Vehicle() {
        this.orders = new ArrayList<>();
    }

    public Vehicle(String vinNumber, String make, String model, Year year, String color, String licensePlate,
                   CarType carType, int currentMileage, int cityMpg, int highwayMpg, Boolean isAvailable, double rate,
                   String vehicleImgSrc) {
        this.vinNumber = vinNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.licensePlate = licensePlate;
        this.carType = carType;
        this.currentMileage = currentMileage;
        this.cityMpg = cityMpg;
        this.highwayMpg = highwayMpg;
        this.isAvailable = isAvailable;
        this.rate = rate;
        this.vehicleImgSrc = vehicleImgSrc;
        this.orders = new ArrayList<>();
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
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

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public int getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(int currentMileage) {
        this.currentMileage = currentMileage;
    }

    public int getCityMpg() {
        return cityMpg;
    }

    public void setCityMpg(int cityMpg) {
        this.cityMpg = cityMpg;
    }

    public int getHighwayMpg() {
        return highwayMpg;
    }

    public void setHighwayMpg(int highwayMpg) {
        this.highwayMpg = highwayMpg;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getVehicleImgSrc() {
        return vehicleImgSrc;
    }

    public void setVehicleImgSrc(String vehicleImgSrc) {
        this.vehicleImgSrc = vehicleImgSrc;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setVehicle(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setVehicle(null);
    }
}
