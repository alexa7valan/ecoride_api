package co.edu.umanizales.ecorideapi2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Passenger extends Person {
    private String membershipLevel;
    private int totalTrips;

    public Passenger(String id, String name, String email, String phone, String membershipLevel, int totalTrips) {
        super(id, name, email, phone);
        this.membershipLevel = membershipLevel;
        this.totalTrips = totalTrips;
    }
}
