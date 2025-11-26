package co.edu.umanizales.ecorideapi2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScooterDTO {
    private String id;
    private String brand;
    private String model;
    private int year;
    private double batteryCapacity;
    private String licensePlate;
    private double maxSpeed;
    private double weight;
}
