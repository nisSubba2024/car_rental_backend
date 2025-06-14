package org.example.carrentalbackend.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Payment findByCardNumber(String cardNumber);

    Payment findByUserEmail(String userEmail);
}
