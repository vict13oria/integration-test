package com.example.demo.service;

import com.example.demo.domain.Vehicle;
import com.example.demo.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    /*** Implementation for all service methods... ****/

// Special case buisness logic
    private static void validateVehicleHelper(Vehicle vehicle) {
        int vinLength = vehicle.getVin().length();
        /*
         * Fun Fact: Prior to 1981, VINs varied in length from 11 to 17 characters. Auto
         * checking on vehicles older than 1981 can resulted in limited info.
         */
        if (vinLength > 17 || vinLength < 11) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "VIN is an invalid length");
        }
        if (vinLength < 17 && vehicle.getYear() >= 1981) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "VIN length is invalid for the declared year");
        }
    }

    public Vehicle createVehicle(Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }
    public Vehicle updateVehicle(String vin, Vehicle vehicle){
        Vehicle vehicle1 = vehicleRepository.findById(vin).get();
        vehicle1.setMake(vehicle.getMake());
        vehicle1.setModel(vehicle.getModel());
        vehicle1.setIs_older(vehicle.getIs_older());
        vehicle1.setYear(vehicle.getYear());
        return vehicleRepository.save(vehicle1);
    }
    public Vehicle deleteVehicle(String vehicleVin){
        vehicleRepository.deleteById(vehicleVin);
        return vehicleRepository.findById(vehicleVin).get();
    }

    public Optional<Vehicle> getVehicleByVin(String vehicleVin) {
        return vehicleRepository.findById(vehicleVin);
    }
}
