package co.edu.umanizales.ecorideapi2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Driver extends Person {
    private String licenseNumber;
    private int experienceYears;

    public Driver(String id, String name, String email, String phone, String licenseNumber, int experienceYears) {
        super(id, name, email, phone);
        this.licenseNumber = licenseNumber;
        this.experienceYears = experienceYears;
    }
}
