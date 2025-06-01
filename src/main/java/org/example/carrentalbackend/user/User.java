package org.example.carrentalbackend.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.example.carrentalbackend.address.Address;
import org.example.carrentalbackend.order.Order;
import org.example.carrentalbackend.order.OrderStatus;
import org.example.carrentalbackend.payment.Payment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    @JsonProperty("userId")
    @UuidGenerator
    private UUID userId;

    @Column(nullable = false)
    @JsonProperty("firstName")
    @NotBlank(message = "First name is blank")
    private String firstName;

    @Column(nullable = false)
    @JsonProperty("lastName")
    @NotBlank(message = "Last name is blank")
    private String lastName;

    @Column(nullable = false, unique = true)
    @JsonProperty("username")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Column(nullable = false, unique = true)
    @JsonProperty("email")
    @Email
    @NotBlank(message = "Email is blank")
    private String email;

    @Column(nullable = false)
    @JsonProperty("password")
    @NotBlank(message = "Password is blank")
    private String password;

    @Column(name = "phone_number", nullable = false, unique = true)
    @JsonProperty("phoneNumber")
    @NotBlank(message = "Phone number is blank")
    @Pattern(regexp = "[0-9]{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "userRole", access = JsonProperty.Access.READ_ONLY)
    @NotNull
    private Role userRole;

    @JsonProperty("addresses")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Valid
    private List<Address> addresses;

    @JsonProperty("orders")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "user-orders")
    @Valid
    private List<Order> orders;

    @JsonProperty("payments")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "user-payments")
    @Valid
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Payment> payments;

    @Column(name = "account_created_on", nullable = false)
    @CreationTimestamp
    private LocalDateTime accountCreatedDate;

    public User() {
        this.userRole = Role.USER;
        this.addresses = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.payments = new ArrayList<>();
    }


    public User(UUID userId, String firstName, String lastName, String username, String email, String password,
                String phoneNumber, LocalDateTime accountCreatedDate) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userRole = Role.USER;
        this.addresses = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.accountCreatedDate = accountCreatedDate;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID customerId) {
        this.userId = customerId;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
        address.setUser(this);
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
        address.setUser(null);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role role) {
        this.userRole = role;
    }


    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }

    public void cancelOrder(Order order) {
        order.setOrderStatus(OrderStatus.CANCELLED);
    }

    public LocalDateTime getAccountCreatedDate() {
        return accountCreatedDate;
    }

    public void setAccountCreatedDate(LocalDateTime accountCreatedDate) {
        this.accountCreatedDate = accountCreatedDate;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setUser(this);
    }

    public void removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setUser(null);
    }

    @Override
    public String toString() {
        return "{" +
                "\n\tUserId: " + getUserId() +
                "\n\tFirstName: " + getFirstName() +
                "\n\tLastName: " + getLastName() +
                "\n\tUsername: " + getUsername() +
                "\n\tEmail: " + getEmail() +
                "\n\tPassword: " + getPassword() +
                "\n\tPhoneNumber: " + getPhoneNumber() +
                "\n\tRole: " + getUserRole() +
                "\n\tAddresses: " + getAddresses() +
                "\n\tOrders: " + getOrders() +
                "\n\tPayments: " + getPayments() +
                "\n\tAccountCreatedDate: " + getAccountCreatedDate() +
                "\n}";
    }
}
