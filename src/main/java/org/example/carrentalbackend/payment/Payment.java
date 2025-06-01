package org.example.carrentalbackend.payment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.carrentalbackend.user.User;
import org.example.carrentalbackend.utility.ExpirationDateDeserializer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.UUID;

@Entity
@Table(name = "payment_methods")
public class Payment {
    @Id
    @Column(name = "payment_method_id", nullable = false, unique = true, updatable = false)
    @UuidGenerator
    @JsonProperty("paymentMethodId")
    private UUID paymentMethodId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty("user")
    @Valid
    @JsonBackReference(value = "user-payments")
    private User user;

    @Column(name = "card_type", nullable = false)
    @JsonProperty("cardType")
    @NotNull(message = "Card type is blank")
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(name = "card_network")
    @JsonProperty("cardNetwork")
    @NotNull(message = "Card network is blank")
    @Enumerated(EnumType.STRING)
    private CardNetwork cardNetwork;

    @Column(name = "card_owner_name", nullable = false)
    @JsonProperty("cardOwnerName")
    @NotBlank(message = "Card owner name is blank")
    private String cardOwnerName;

    @Column(name = "card_number", nullable = false)
    @JsonProperty("cardNumber")
    @NotBlank(message = "Card number is blank")
    @Size(min = 16, message = "Card number must be 16 character long")
    private String cardNumber;

    @Column(name = "expiration_date", nullable = false)
    @JsonProperty("expirationDate")
    @JsonDeserialize(using = ExpirationDateDeserializer.class)
    @NotNull(message = "Expiration date is blank")
    private YearMonth expirationDate;
    // Format: "2015-01"

    @Column(name = "card_ccv", nullable = false)
    @JsonProperty("cardCvv")
    @NotBlank(message = "Card cvv is blank")
    @Size(min = 3, max = 4, message = "CVV is wrong")
    private String cardCvv;

    @CreationTimestamp
    @JsonProperty("addedOn")
    private LocalDateTime addedOn;

//    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference(value="order-payment")
//    @Valid
//    private List<Order> orders;

    public Payment() {
//        this.orders = new ArrayList<>();

    }

    public Payment(UUID paymentMethodId, User user, CardType cardType, CardNetwork cardNetwork,
                   String cardOwnerName,
                   String cardNumber,
                   YearMonth expirationDate
            , String cardCvv, LocalDateTime addedOn) {
        this.paymentMethodId = paymentMethodId;
        this.user = user;
        this.cardType = cardType;
        this.cardNetwork = cardNetwork;
        this.cardOwnerName = cardOwnerName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cardCvv = cardCvv;
        this.addedOn = addedOn;
//        this.orders = new ArrayList<>();
    }

    public UUID getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(UUID paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardNetwork getCardNetwork() {
        return cardNetwork;
    }

    public void setCardNetwork(CardNetwork cardNetwork) {
        this.cardNetwork = cardNetwork;
    }

    public String getCardOwnerName() {
        return cardOwnerName;
    }

    public void setCardOwnerName(String cardOwnerName) {
        this.cardOwnerName = cardOwnerName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(YearMonth expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }
}
//    public List<Order> getOrders() {return orders;}
//    public void setOrders(List<Order> orders) {this.orders = orders;}
//    public void addOrder(Order order) {this.orders.add(order);
//    order.setPayment(this);}
//}
