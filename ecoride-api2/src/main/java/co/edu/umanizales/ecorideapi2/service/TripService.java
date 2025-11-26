package co.edu.umanizales.ecorideapi2.service;

import co.edu.umanizales.ecorideapi2.dto.TripDTO;
import co.edu.umanizales.ecorideapi2.model.Trip;
import co.edu.umanizales.ecorideapi2.repository.TripRepository;
import co.edu.umanizales.ecorideapi2.repository.DriverRepository;
import co.edu.umanizales.ecorideapi2.repository.CarRepository;
import co.edu.umanizales.ecorideapi2.repository.ScooterRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;
    private final ScooterRepository scooterRepository;

    public TripService(TripRepository tripRepository, DriverRepository driverRepository,
                       CarRepository carRepository, ScooterRepository scooterRepository) {
        this.tripRepository = tripRepository;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.scooterRepository = scooterRepository;
    }

    public List<TripDTO> getAllTrips() {
        try {
            return tripRepository.findAll().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving trips", e);
        }
    }

    public TripDTO getTripById(String id) {
        try {
            return tripRepository.findById(id)
                    .map(this::toDTO)
                    .orElseThrow(() -> new RuntimeException("Trip not found with id: " + id));
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving trip", e);
        }
    }

    public TripDTO createTrip(TripDTO tripDTO) {
        try {
            validateTrip(tripDTO);
            
            if (tripDTO.getId() == null || tripDTO.getId().isEmpty()) {
                tripDTO.setId(UUID.randomUUID().toString());
            }
            Trip trip = toEntity(tripDTO);
            Trip savedTrip = tripRepository.save(trip);
            return toDTO(savedTrip);
        } catch (IOException e) {
            throw new RuntimeException("Error creating trip", e);
        }
    }

    public TripDTO updateTrip(String id, TripDTO tripDTO) {
        try {
            tripRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Trip not found with id: " + id));
            validateTrip(tripDTO);
            tripDTO.setId(id);
            Trip trip = toEntity(tripDTO);
            Trip updatedTrip = tripRepository.save(trip);
            return toDTO(updatedTrip);
        } catch (IOException e) {
            throw new RuntimeException("Error updating trip", e);
        }
    }

    public void deleteTrip(String id) {
        try {
            tripRepository.deleteById(id);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting trip", e);
        }
    }

    private void validateTrip(TripDTO tripDTO) throws IOException {
        // Validate driver exists
        driverRepository.findById(tripDTO.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + tripDTO.getDriverId()));
        
        // Validate vehicle exists (either car or scooter)
        boolean vehicleExists = carRepository.findById(tripDTO.getVehicleId()).isPresent() ||
                               scooterRepository.findById(tripDTO.getVehicleId()).isPresent();
        
        if (!vehicleExists) {
            throw new RuntimeException("Vehicle not found with id: " + tripDTO.getVehicleId());
        }
    }

    private TripDTO toDTO(Trip trip) {
        return new TripDTO(
                trip.getId(),
                trip.getDriverId(),
                trip.getVehicleId(),
                trip.getDistance(),
                trip.getStartTime(),
                trip.getEndTime(),
                trip.getOrigin(),
                trip.getDestination()
        );
    }

    private Trip toEntity(TripDTO dto) {
        return new Trip(
                dto.getId(),
                dto.getDriverId(),
                dto.getVehicleId(),
                dto.getDistance(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getOrigin(),
                dto.getDestination()
        );
    }
}
