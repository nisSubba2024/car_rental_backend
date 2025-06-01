package org.example.carrentalbackend.vehicle;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle findVehicleById(String vehicleId) {
        return vehicleRepository.findById(vehicleId).orElseThrow(() -> new IllegalArgumentException("Vehicle not " +
                "found"));
    }

    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Vehicle saveNewVehicle(Vehicle vehicle) {
        Year year = Year.now();

        if (vehicle.getYear().isAfter(year)) {
            throw new IllegalArgumentException("Invalid vehicle year");
        }

        if (vehicleRepository.existsById(vehicle.getVinNumber())) {
            throw new IllegalArgumentException("Vin number already exists");
        }

        return vehicleRepository.save(vehicle);
    }

    @Transactional(rollbackFor = Exception.class)
    public Vehicle deleteVehicle(String vehicleId) {
        Vehicle vehicle = findVehicleById(vehicleId);
        vehicleRepository.delete(vehicle);
        return vehicle;
    }

    @Transactional(rollbackFor = Exception.class)
    public Vehicle updateVehicle(Vehicle vehicle) {

        Vehicle vehicleToUpdate = vehicleRepository.findById(vehicle.getVinNumber()).orElseThrow(() -> new IllegalArgumentException(
                "Vin number not found"));

        if (vehicle.getMake() != null) {
            vehicleToUpdate.setMake(vehicle.getMake());
        }

        if (vehicle.getModel() != null) {
            vehicleToUpdate.setModel(vehicle.getModel());
        }

        if (vehicle.getYear() != null) {
            vehicleToUpdate.setYear(vehicle.getYear());
        }

        if (vehicle.getColor() != null) {
            vehicleToUpdate.setColor(vehicle.getColor());
        }

        if (vehicle.getLicensePlate() != null) {
            vehicleToUpdate.setLicensePlate(vehicle.getLicensePlate());
        }
        if (vehicle.getCarType() != null) {
            vehicleToUpdate.setCarType(vehicle.getCarType());
        }

        if (vehicle.getCurrentMileage() >= 0) {
            vehicleToUpdate.setCurrentMileage(vehicle.getCurrentMileage());
        }
        if (vehicle.getCityMpg() > 0) {
            vehicleToUpdate.setCityMpg(vehicle.getCityMpg());
        }

        if (vehicle.getHighwayMpg() > 0) {
            vehicleToUpdate.setHighwayMpg(vehicle.getHighwayMpg());
        }
        if (vehicle.getIsAvailable() != null) {
            vehicleToUpdate.setIsAvailable(vehicle.getIsAvailable());
        }

        if (vehicle.getRate() > 0) {
            vehicleToUpdate.setRate(vehicle.getRate());
        }

        if (vehicle.getVehicleImgSrc() != null) {
            vehicleToUpdate.setVehicleImgSrc(vehicle.getVehicleImgSrc());
        }

        return vehicleRepository.save(vehicleToUpdate);
    }
}
