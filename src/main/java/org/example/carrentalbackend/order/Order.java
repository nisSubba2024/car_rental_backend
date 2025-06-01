package org.example.carrentalbackend.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import org.example.carrentalbackend.payment.Payment;
import org.example.carrentalbackend.user.User;
import org.example.carrentalbackend.vehicle.Vehicle;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @JsonProperty("orderId")
    @Column(name = "order_id", nullable = false, updatable = false)
    private String orderId;

    @JsonProperty("user")
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "user-orders")
    private User user;

    @JsonProperty("vehicle")
    @JoinColumn(name = "vehicle_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "vehicle-order")
    private Vehicle vehicle;

    @Column(name = "reservation_date_time", nullable = false)
    @JsonProperty("reservationDateTime")
    @Future
    private LocalDateTime reservationDateTime;

    @Column(name = "return_date_time", nullable = false)
    @JsonProperty("returnDateTime")
    @Future
    private LocalDateTime returnDateTime;

    @Column(name = "order_status", nullable = false)
    @JsonProperty("orderStatus")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @JoinColumn(name = "payment_method_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("order-payment")
    @Valid
    private Payment payment;

    @Column(name = "order_date_time", nullable = false, updatable = false)
    @CreationTimestamp
    @JsonProperty("orderDateTime")
    private LocalDateTime orderDateTime;

    public Order() {
        this.orderStatus = OrderStatus.PENDING;
    }

    public Order(String orderId, User user, Vehicle vehicle, LocalDateTime reservationDateTime,
                 LocalDateTime returnDateTime, Payment payment, LocalDateTime orderDateTime) {
        this.orderId = orderId;
        this.user = user;
        this.vehicle = vehicle;
        this.reservationDateTime = reservationDateTime;
        this.returnDateTime = returnDateTime;
        this.orderStatus = OrderStatus.PENDING;
        this.payment = payment;
        this.orderDateTime = orderDateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDate) {
        this.reservationDateTime = reservationDate;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDate) {
        this.returnDateTime = returnDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

}
