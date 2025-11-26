package co.edu.umanizales.ecorideapi2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vehicle {
    private String id;
    private String brand;
    private String model;
    private int year;
    private double batteryCapacity;
    private String licensePlate;
}
