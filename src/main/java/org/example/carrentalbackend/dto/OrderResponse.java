package org.example.carrentalbackend.dto;

import org.example.carrentalbackend.order.OrderStatus;
import org.example.carrentalbackend.payment.CardType;

import java.time.LocalDateTime;

public class OrderResponse {
    private String orderId;
    private String vinNumber;
    private String customerName;
    private String email;
    private LocalDateTime reservationDateTime;
    private LocalDateTime returnDateTime;
    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;
    private CardType cardType;

    public OrderResponse() {
    }

    public OrderResponse(String orderId, String vinNumber, String customerName, String email,
                         LocalDateTime reservationDateTime, LocalDateTime returnDateTime,
                         LocalDateTime orderDateTime, OrderStatus orderStatus,
                         CardType cardType) {
        this.orderId = orderId;
        this.vinNumber = vinNumber;
        this.customerName = customerName;
        this.email = email;
        this.reservationDateTime = reservationDateTime;
        this.returnDateTime = returnDateTime;
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.cardType = cardType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

}
