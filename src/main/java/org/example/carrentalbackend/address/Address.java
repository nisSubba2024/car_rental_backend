package org.example.carrentalbackend.address;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.example.carrentalbackend.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @UuidGenerator
    @Column(name = "address_id", nullable = false, unique = true)
    @JsonProperty("addressId")
    private UUID addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty("userId")
    @JsonBackReference
    @Valid
    private User user;

    @Column(name = "house_number", nullable = false)
    @JsonProperty("houseNumber")
    @Min(value = 1, message = "House number must be a positive integer")
    private int houseNumber;

    @Column(name = "street_name", nullable = false)
    @JsonProperty("streetName")
    @NotBlank(message = "Street name is blank")
    private String streetName;

    @Column(name = "city", nullable = false)
    @JsonProperty("city")
    @NotBlank(message = "City is blank")
    private String city;

    @Column(name = "state", nullable = false)
    @JsonProperty("state")
    @NotBlank(message = "State is blank")
    private String state;

    @Column(name = "zip_code", nullable = false)
    @JsonProperty("zipCode")
    @NotBlank(message = "Zip Code is blank")
    private String zipCode;

    @CreationTimestamp
    @Column(name = "address_added_on", nullable = false)
    private LocalDateTime addressAddedOn;

    public Address() {
    }

    public Address(UUID addressId, User user, int houseNumber, String streetName, String city, String state,
                   String zipCode, LocalDateTime addressAddedOn) {
        this.addressId = addressId;
        this.user = user;
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.addressAddedOn = addressAddedOn;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int streetNumber) {
        this.houseNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public LocalDateTime getAddressAddedOn() {
        return addressAddedOn;
    }

    public void setAddressAddedOn(LocalDateTime addressAddedOn) {
        this.addressAddedOn = addressAddedOn;
    }

}
