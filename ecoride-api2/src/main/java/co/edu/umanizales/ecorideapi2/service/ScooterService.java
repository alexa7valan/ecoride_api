package co.edu.umanizales.ecorideapi2.service;

import co.edu.umanizales.ecorideapi2.dto.ScooterDTO;
import co.edu.umanizales.ecorideapi2.model.Scooter;
import co.edu.umanizales.ecorideapi2.repository.ScooterRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ScooterService {
    private final ScooterRepository scooterRepository;

    public ScooterService(ScooterRepository scooterRepository) {
        this.scooterRepository = scooterRepository;
    }

    public List<ScooterDTO> getAllScooters() {
        try {
            return scooterRepository.findAll().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving scooters", e);
        }
    }

    public ScooterDTO getScooterById(String id) {
        try {
            return scooterRepository.findById(id)
                    .map(this::toDTO)
                    .orElseThrow(() -> new RuntimeException("Scooter not found with id: " + id));
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving scooter", e);
        }
    }

    public ScooterDTO createScooter(ScooterDTO scooterDTO) {
        try {
            if (scooterDTO.getId() == null || scooterDTO.getId().isEmpty()) {
                scooterDTO.setId(UUID.randomUUID().toString());
            }
            Scooter scooter = toEntity(scooterDTO);
            Scooter savedScooter = scooterRepository.save(scooter);
            return toDTO(savedScooter);
        } catch (IOException e) {
            throw new RuntimeException("Error creating scooter", e);
        }
    }

    public ScooterDTO updateScooter(String id, ScooterDTO scooterDTO) {
        try {
            scooterRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Scooter not found with id: " + id));
            scooterDTO.setId(id);
            Scooter scooter = toEntity(scooterDTO);
            Scooter updatedScooter = scooterRepository.save(scooter);
            return toDTO(updatedScooter);
        } catch (IOException e) {
            throw new RuntimeException("Error updating scooter", e);
        }
    }

    public void deleteScooter(String id) {
        try {
            scooterRepository.deleteById(id);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting scooter", e);
        }
    }

    private ScooterDTO toDTO(Scooter scooter) {
        return new ScooterDTO(
                scooter.getId(),
                scooter.getBrand(),
                scooter.getModel(),
                scooter.getYear(),
                scooter.getBatteryCapacity(),
                scooter.getLicensePlate(),
                scooter.getMaxSpeed(),
                scooter.getWeight()
        );
    }

    private Scooter toEntity(ScooterDTO dto) {
        return new Scooter(
                dto.getId(),
                dto.getBrand(),
                dto.getModel(),
                dto.getYear(),
                dto.getBatteryCapacity(),
                dto.getLicensePlate(),
                dto.getMaxSpeed(),
                dto.getWeight()
        );
    }
}
