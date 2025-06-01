package org.example.carrentalbackend.order;

import org.example.carrentalbackend.user.User;
import org.example.carrentalbackend.user.UserRepository;
import org.example.carrentalbackend.vehicle.Vehicle;
import org.example.carrentalbackend.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> findOrdersByUsername(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("User with username " + username + " not found");
        }
        return orderRepository.findByUserUsername(username);
    }

    public List<Order> findOrdersByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with username " + email + " not found");
        }
        return orderRepository.findByUserEmail(email);
    }

    public List<Order> findOrdersByPhoneNumber(String phoneNumber) {
        if (!userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("User with username " + phoneNumber + " not found");
        }
        return orderRepository.findByUserPhoneNumber(phoneNumber);
    }

    // Admin canceling order
    @Transactional(rollbackFor = Exception.class)
    public String cancelOrderByOrderId(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new java.lang.IllegalArgumentException("Order with id " + orderId + " not " +
                "found"));

        // Order must exist at this point
        User user = order.getUser();

        user.cancelOrder(order);

        Vehicle vehicle = order.getVehicle();
        vehicle.setIsAvailable(true);

        orderRepository.delete(order);
        userRepository.save(user);
        vehicleRepository.save(vehicle);

        return orderId + " has been successfully cancelled";
    }


}
