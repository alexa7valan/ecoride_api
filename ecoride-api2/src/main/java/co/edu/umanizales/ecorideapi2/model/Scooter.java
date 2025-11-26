package co.edu.umanizales.ecorideapi2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Scooter extends Vehicle {
    private double maxSpeed;
    private double weight;

    public Scooter(String id, String brand, String model, int year, double batteryCapacity, 
                   String licensePlate, double maxSpeed, double weight) {
        super(id, brand, model, year, batteryCapacity, licensePlate);
        this.maxSpeed = maxSpeed;
        this.weight = weight;
    }
}
