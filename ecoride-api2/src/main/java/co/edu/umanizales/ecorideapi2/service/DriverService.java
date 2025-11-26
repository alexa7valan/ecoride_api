package co.edu.umanizales.ecorideapi2.service;

import co.edu.umanizales.ecorideapi2.dto.DriverDTO;
import co.edu.umanizales.ecorideapi2.model.Driver;
import co.edu.umanizales.ecorideapi2.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<DriverDTO> getAllDrivers() {
        try {
            return driverRepository.findAll().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving drivers", e);
        }
    }

    public DriverDTO getDriverById(String id) {
        try {
            return driverRepository.findById(id)
                    .map(this::toDTO)
                    .orElseThrow(() -> new RuntimeException("Driver not found with id: " + id));
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving driver", e);
        }
    }

    public DriverDTO createDriver(DriverDTO driverDTO) {
        try {
            if (driverDTO.getId() == null || driverDTO.getId().isEmpty()) {
                driverDTO.setId(UUID.randomUUID().toString());
            }
            Driver driver = toEntity(driverDTO);
            Driver savedDriver = driverRepository.save(driver);
            return toDTO(savedDriver);
        } catch (IOException e) {
            throw new RuntimeException("Error creating driver", e);
        }
    }

    public DriverDTO updateDriver(String id, DriverDTO driverDTO) {
        try {
            driverRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Driver not found with id: " + id));
            driverDTO.setId(id);
            Driver driver = toEntity(driverDTO);
            Driver updatedDriver = driverRepository.save(driver);
            return toDTO(updatedDriver);
        } catch (IOException e) {
            throw new RuntimeException("Error updating driver", e);
        }
    }

    public void deleteDriver(String id) {
        try {
            driverRepository.deleteById(id);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting driver", e);
        }
    }

    private DriverDTO toDTO(Driver driver) {
        return new DriverDTO(
                driver.getId(),
                driver.getName(),
                driver.getEmail(),
                driver.getPhone(),
                driver.getLicenseNumber(),
                driver.getExperienceYears()
        );
    }

    private Driver toEntity(DriverDTO dto) {
        return new Driver(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getLicenseNumber(),
                dto.getExperienceYears()
        );
    }
}
