package co.edu.umanizales.ecorideapi2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    private String id;
    private String driverId;
    private String vehicleId;
    private double distance;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String origin;
    private String destination;
}
