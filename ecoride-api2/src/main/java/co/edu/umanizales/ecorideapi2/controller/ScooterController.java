package co.edu.umanizales.ecorideapi2.controller;

import co.edu.umanizales.ecorideapi2.dto.ScooterDTO;
import co.edu.umanizales.ecorideapi2.service.ScooterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scooters")
public class ScooterController {
    private final ScooterService scooterService;

    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @GetMapping
    public ResponseEntity<List<ScooterDTO>> getAllScooters() {
        return ResponseEntity.ok(scooterService.getAllScooters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScooterDTO> getScooterById(@PathVariable String id) {
        return ResponseEntity.ok(scooterService.getScooterById(id));
    }

    @PostMapping
    public ResponseEntity<ScooterDTO> createScooter(@RequestBody ScooterDTO scooterDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(scooterService.createScooter(scooterDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScooterDTO> updateScooter(@PathVariable String id, 
                                                     @RequestBody ScooterDTO scooterDTO) {
        return ResponseEntity.ok(scooterService.updateScooter(id, scooterDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScooter(@PathVariable String id) {
        scooterService.deleteScooter(id);
        return ResponseEntity.noContent().build();
    }
}
