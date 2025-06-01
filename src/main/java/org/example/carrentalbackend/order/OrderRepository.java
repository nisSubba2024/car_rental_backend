package org.example.carrentalbackend.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserUsername(String username);

    List<Order> findByUserEmail(String email);

    List<Order> findByUserPhoneNumber(String phoneNumber);
}
