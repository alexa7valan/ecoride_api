package co.edu.umanizales.ecorideapi2.controller;

import co.edu.umanizales.ecorideapi2.dto.TripDTO;
import co.edu.umanizales.ecorideapi2.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDTO> getTripById(@PathVariable String id) {
        return ResponseEntity.ok(tripService.getTripById(id));
    }

    @PostMapping
    public ResponseEntity<TripDTO> createTrip(@RequestBody TripDTO tripDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tripService.createTrip(tripDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripDTO> updateTrip(@PathVariable String id, 
                                              @RequestBody TripDTO tripDTO) {
        return ResponseEntity.ok(tripService.updateTrip(id, tripDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable String id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
}
