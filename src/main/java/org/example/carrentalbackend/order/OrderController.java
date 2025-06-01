package org.example.carrentalbackend.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            return ResponseEntity.ok().body(orderService.findAllOrders());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<?> createUserOrder(@PathVariable String orderId) {
        try {
            return ResponseEntity.ok().body(orderService.cancelOrderByOrderId(orderId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserOrderByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok().body(orderService.findOrdersByUsername(username));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUserOrderByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok().body(orderService.findOrdersByEmail(email));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{phoneNumber}")
    public ResponseEntity<?> getUserOrderByPhoneNumber(@PathVariable String phoneNumber) {
        try {
            return ResponseEntity.ok().body(orderService.findOrdersByPhoneNumber(phoneNumber));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
