package co.edu.umanizales.ecorideapi2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Car extends Vehicle {
    private int numberOfDoors;
    private int seatingCapacity;

    public Car(String id, String brand, String model, int year, double batteryCapacity, 
               String licensePlate, int numberOfDoors, int seatingCapacity) {
        super(id, brand, model, year, batteryCapacity, licensePlate);
        this.numberOfDoors = numberOfDoors;
        this.seatingCapacity = seatingCapacity;
    }
}
