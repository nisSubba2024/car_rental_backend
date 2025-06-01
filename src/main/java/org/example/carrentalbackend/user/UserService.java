package org.example.carrentalbackend.user;

import jakarta.validation.Valid;
import org.example.carrentalbackend.address.Address;
import org.example.carrentalbackend.address.AddressRepository;
import org.example.carrentalbackend.dto.*;
import org.example.carrentalbackend.order.Order;
import org.example.carrentalbackend.order.OrderRepository;
import org.example.carrentalbackend.payment.Payment;
import org.example.carrentalbackend.payment.PaymentRepository;
import org.example.carrentalbackend.security.CustomUserDetail;
import org.example.carrentalbackend.utility.RandomIdGenerator;
import org.example.carrentalbackend.vehicle.Vehicle;
import org.example.carrentalbackend.vehicle.VehicleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;
    private final RandomIdGenerator randomIdGenerator;
    private final VehicleRepository vehicleRepository;
    private final PaymentRepository paymentRepository;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, AddressRepository addressRepository,
                       PasswordEncoder passwordEncoder, OrderRepository orderRepository,
                       RandomIdGenerator randomIdGenerator, VehicleRepository vehicleRepository,
                       PaymentRepository paymentRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderRepository = orderRepository;
        this.randomIdGenerator = randomIdGenerator;
        this.vehicleRepository = vehicleRepository;
        this.paymentRepository = paymentRepository;
        this.authenticationManager = authenticationManager;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number is already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public LoginResponse login(LoginRequest loginRequest) {
        System.out.println(loginRequest.getUsername() + loginRequest.getPassword());
        if (userRepository.findByUsername(loginRequest.getUsername()).isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword());
        Authentication authenticatedResponse = this.authenticationManager.authenticate(authenticationRequest);
        CustomUserDetail customUserDetail = (CustomUserDetail) authenticatedResponse.getPrincipal();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setFirstName(customUserDetail.getUsername());
        loginResponse.setLastName(loginRequest.getUsername());
        loginResponse.setUsername(customUserDetail.getUsername());
        loginResponse.setEmail(customUserDetail.getUsername());
        return loginResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public User updateUserByUsername(String username, User updatedUser) {
        User existingUser = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(
                "Username " + username + " not found"));
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }

        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        }

        return userRepository.save(existingUser);
    }

    @Transactional(rollbackFor = Exception.class)
    public User changePassword(String username, PasswordRequest newPassword) {
        User existingUser = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(
                "Username " + username + " not found"));

        if (newPassword != null) {
            existingUser.setPassword(newPassword.getPassword());
        }

        return userRepository.save(existingUser);
    }

    @Transactional(rollbackFor = Exception.class)
    public User deleteUser(String username) {
        User existingUser = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(
                "Username " + username + " not found"
        ));

        userRepository.delete(existingUser);
        return existingUser;
    }


    // Address
    @Transactional(rollbackFor = Exception.class)
    public Address saveAddress(String username, Address address) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Username " + username + " not found"));
        address.setUser(user);
        user.addAddress(address);
        return addressRepository.save(address);
    }

    public List<Address> getAllAddresses(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Username " + username + " not found"));
        return user.getAddresses();
    }

    @Transactional(rollbackFor = Exception.class)
    public Address deleteAddress(String username, int index) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found."));
        int userAddressCount = user.getAddresses().size();

        if (index < 0 || index >= userAddressCount) {
            throw new IllegalArgumentException("Invalid address delete index");
        }

        Address address = user.getAddresses().get(index);
        user.getAddresses().remove(index);
        userRepository.save(user);
        return address;
    }


    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<User> findUserByUsername(String username) {
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new IllegalArgumentException("Username does not exist");
        }
        return userRepository.findByUsername(username);
    }

    public String generateOrderId() {
        String orderId;
        do {
            orderId = randomIdGenerator.generateId(10);
        } while (orderRepository.existsById(orderId));
        return orderId;
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderResponse createOrder(String username, OrderRequest orderRequest) {

        LocalDateTime currentDateTime = LocalDateTime.now();

        if (orderRequest.getReservationDateTime().isBefore(currentDateTime) || orderRequest.getReturnDateTime().isBefore(currentDateTime) || orderRequest.getReturnDateTime().isBefore(orderRequest.getReservationDateTime())) {
            throw new IllegalArgumentException("Invalid reservation date");
        }

        String orderId = generateOrderId();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not " +
                "found"));

        Vehicle vehicle = vehicleRepository.findById(orderRequest.getVinNumber()).orElseThrow(() -> new IllegalArgumentException("Vin not " +
                "found"));

        if (vehicle.getIsAvailable() == false) {
            throw new IllegalArgumentException("Vehicle is not available");
        }

        Payment payment = paymentRepository.findById(orderRequest.getPaymentId()).orElseThrow(() -> new IllegalArgumentException("Payment " +
                "not found"));

        Order order = new Order(orderId, user, vehicle, orderRequest.getReservationDateTime(),
                orderRequest.getReturnDateTime(), payment, LocalDateTime.now());

        user.getOrders().add(order);
        vehicle.setIsAvailable(false);
        vehicle.getOrders().add(order);
        userRepository.save(user);
        vehicleRepository.save(vehicle);

        String customerName = user.getFirstName() + " " + user.getLastName();

        return new OrderResponse(orderId, vehicle.getVinNumber(), customerName, orderRequest.getEmail(),
                order.getReservationDateTime(), order.getReturnDateTime(), order.getOrderDateTime(),
                order.getOrderStatus(), order.getPayment().getCardType());
    }

    // add payment method

    @Transactional(rollbackFor = Exception.class)
    public void savePayment(@Valid User user, @Valid Payment payment) {
        // verify expiration date
        YearMonth currentDate = YearMonth.now();

        if (payment.getExpirationDate().isBefore(currentDate)) {
            throw new IllegalArgumentException("Payment expired");
        }

        user.addPayment(payment);
        userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public String addPaymentMethod(String email, @Valid Payment payment) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User does not " +
                "exist with " + email + " account"));

        savePayment(user, payment);

        return "Payment method successfully added to user";
    }

    @Transactional(rollbackFor = Exception.class)
    public String deletePaymentMethod(String email, Payment payment) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User does not " +
                "exist with " + email + " account"));

        user.removePayment(payment);
        userRepository.save(user);
        paymentRepository.save(payment);

        return "Payment method successfully removed from user";
    }

}
