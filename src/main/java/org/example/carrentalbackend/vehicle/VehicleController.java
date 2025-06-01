package org.example.carrentalbackend.vehicle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    public final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<?> getAllVehicle() {
        try {
            return ResponseEntity.ok().body(vehicleService.findAllVehicles());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{vinNumber}")
    public ResponseEntity<?> getVehicleById(@PathVariable String vinNumber) {
        try {
            return ResponseEntity.ok().body(vehicleService.findVehicleById(vinNumber));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody Vehicle vehicle) {
        try {
            return ResponseEntity.ok().body(vehicleService.saveNewVehicle(vehicle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{vinNumber}")
    public ResponseEntity<?> deleteVehicle(@PathVariable String vinNumber) {
        try {
            return ResponseEntity.ok().body(vehicleService.deleteVehicle(vinNumber));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{vinNumber}")
    public ResponseEntity<?> updateVehicle(@RequestBody Vehicle vehicle) {
        try {
            return ResponseEntity.ok().body(vehicleService.updateVehicle(vehicle));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
