package co.edu.umanizales.ecorideapi2.service;

import co.edu.umanizales.ecorideapi2.dto.CarDTO;
import co.edu.umanizales.ecorideapi2.model.Car;
import co.edu.umanizales.ecorideapi2.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarDTO> getAllCars() {
        try {
            return carRepository.findAll().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving cars", e);
        }
    }

    public CarDTO getCarById(String id) {
        try {
            return carRepository.findById(id)
                    .map(this::toDTO)
                    .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving car", e);
        }
    }

    public CarDTO createCar(CarDTO carDTO) {
        try {
            if (carDTO.getId() == null || carDTO.getId().isEmpty()) {
                carDTO.setId(UUID.randomUUID().toString());
            }
            Car car = toEntity(carDTO);
            Car savedCar = carRepository.save(car);
            return toDTO(savedCar);
        } catch (IOException e) {
            throw new RuntimeException("Error creating car", e);
        }
    }

    public CarDTO updateCar(String id, CarDTO carDTO) {
        try {
            carRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
            carDTO.setId(id);
            Car car = toEntity(carDTO);
            Car updatedCar = carRepository.save(car);
            return toDTO(updatedCar);
        } catch (IOException e) {
            throw new RuntimeException("Error updating car", e);
        }
    }

    public void deleteCar(String id) {
        try {
            carRepository.deleteById(id);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting car", e);
        }
    }

    private CarDTO toDTO(Car car) {
        return new CarDTO(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getBatteryCapacity(),
                car.getLicensePlate(),
                car.getNumberOfDoors(),
                car.getSeatingCapacity()
        );
    }

    private Car toEntity(CarDTO dto) {
        return new Car(
                dto.getId(),
                dto.getBrand(),
                dto.getModel(),
                dto.getYear(),
                dto.getBatteryCapacity(),
                dto.getLicensePlate(),
                dto.getNumberOfDoors(),
                dto.getSeatingCapacity()
        );
    }
}
