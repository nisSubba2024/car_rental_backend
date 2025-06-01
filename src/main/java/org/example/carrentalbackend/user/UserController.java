package org.example.carrentalbackend.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.carrentalbackend.address.Address;
import org.example.carrentalbackend.dto.LoginRequest;
import org.example.carrentalbackend.dto.OrderRequest;
import org.example.carrentalbackend.dto.PasswordRequest;
import org.example.carrentalbackend.error.ErrorResponse;
import org.example.carrentalbackend.error.RequestStatus;
import org.example.carrentalbackend.payment.Payment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body((userService.findAll()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return ResponseEntity.created(URI.create("/users/" + newUser.getUserId())).body("User Registered" +
                    " successfully");
        } catch (java.lang.IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(400, RequestStatus.FAILED, e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok().body(userService.findUserByUsername(username));
        } catch (java.lang.IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/username/{username}")
    public ResponseEntity<?> updateUserByUsername(@PathVariable String username, @Valid @RequestBody User updatedUser) {
        try {
            System.out.println(updatedUser.toString());
            System.out.println("User updated successfully");
            return ResponseEntity.ok().body(userService.updateUserByUsername(username, updatedUser));

        } catch (java.lang.IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/password/username/{username}")
    public ResponseEntity<?> updateAccountByUsername(@PathVariable String username,
                                                     @RequestBody PasswordRequest newPassword) {
        try {
            System.out.println("Password changed successfully");
            return ResponseEntity.ok().body(userService.changePassword(username, newPassword));
        } catch (java.lang.IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok().body(userService.login(loginRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok().body(userService.deleteUser(username));
        } catch (java.lang.IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{username}/save")
    public ResponseEntity<?> save(@PathVariable String username, @Valid @RequestBody Address address) {
        try {
            Address newAddress = userService.saveAddress(username, address);
            return ResponseEntity.ok().body(newAddress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Address

    @GetMapping("/username/address/{username}")
    public ResponseEntity<?> getAllAddresses(@PathVariable String username) {
        try {
            return ResponseEntity.ok().body(userService.getAllAddresses(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/username/address/{username}/{index}")
    public ResponseEntity<?> deleteAddress(@PathVariable String username, @PathVariable int index) {
        try {
            return ResponseEntity.ok().body(userService.deleteAddress(username, index));
        } catch (java.lang.IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{username}/create-order")
    public ResponseEntity<?> createOrder(@PathVariable String username, @Valid @RequestBody OrderRequest orderRequest) {
        try {
            return ResponseEntity.ok().body(userService.createOrder(username, orderRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("Unexpected error occurred" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/username/payment/{email}")
    public ResponseEntity<?> savePayment(@PathVariable String email, @Valid @RequestBody Payment payment) {
        try {
            return ResponseEntity.ok().body(userService.addPaymentMethod(email, payment));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/username/payment/{email}")
    public ResponseEntity<?> deletePayment(@PathVariable String email, @Valid @RequestBody Payment payment) {
        try {
            System.out.println("Hi, im here");
            return ResponseEntity.ok().body(userService.deletePaymentMethod(email, payment));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @RequestMapping("**")
    public ResponseEntity<String> badRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String message = "The endpoint " + uri + " does not exist.";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
}
