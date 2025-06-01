package org.example.carrentalbackend.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findByUserUserId(UUID userId);

    Optional<Address> findByUserUsername(String username);
}
