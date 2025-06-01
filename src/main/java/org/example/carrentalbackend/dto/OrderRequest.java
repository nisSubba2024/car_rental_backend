package org.example.carrentalbackend.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderRequest {

    private String email;
    private String vinNumber;
    private LocalDateTime reservationDateTime;
    private LocalDateTime returnDateTime;
    private UUID paymentId;

    public OrderRequest() {
    }

    public OrderRequest(String email, String vinNumber, LocalDateTime reservationDateTime,
                        LocalDateTime returnDateTime, UUID paymentId) {
        this.email = email;
        this.vinNumber = vinNumber;
        this.reservationDateTime = reservationDateTime;
        this.returnDateTime = returnDateTime;
        this.paymentId = paymentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
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

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }


}
