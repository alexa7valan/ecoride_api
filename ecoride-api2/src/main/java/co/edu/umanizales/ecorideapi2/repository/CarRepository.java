package co.edu.umanizales.ecorideapi2.repository;

import co.edu.umanizales.ecorideapi2.model.Car;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository implements CsvRepository<Car> {
    private static final String FILE_PATH = "data/cars.csv";
    private static final String HEADER = "id,brand,model,year,batteryCapacity,licensePlate,numberOfDoors,seatingCapacity";

    public CarRepository() {
        initializeFile();
    }

    private void initializeFile() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(HEADER);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error initializing cars CSV file", e);
        }
    }

    @Override
    public List<Car> findAll() throws IOException {
        List<Car> cars = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
        
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!line.trim().isEmpty()) {
                cars.add(parseCar(line));
            }
        }
        return cars;
    }

    @Override
    public Optional<Car> findById(String id) throws IOException {
        return findAll().stream()
                .filter(car -> car.getId().equals(id))
                .findFirst();
    }

    @Override
    public Car save(Car car) throws IOException {
        List<Car> cars = findAll();
        cars.removeIf(c -> c.getId().equals(car.getId()));
        cars.add(car);
        saveAll(cars);
        return car;
    }

    @Override
    public void deleteById(String id) throws IOException {
        List<Car> cars = findAll();
        cars.removeIf(c -> c.getId().equals(id));
        saveAll(cars);
    }

    @Override
    public void saveAll(List<Car> cars) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(HEADER);
            writer.newLine();
            for (Car car : cars) {
                writer.write(toCSV(car));
                writer.newLine();
            }
        }
    }

    private Car parseCar(String line) {
        String[] parts = line.split(",");
        return new Car(
                parts[0],
                parts[1],
                parts[2],
                Integer.parseInt(parts[3]),
                Double.parseDouble(parts[4]),
                parts[5],
                Integer.parseInt(parts[6]),
                Integer.parseInt(parts[7])
        );
    }

    private String toCSV(Car car) {
        return String.join(",",
                car.getId(),
                car.getBrand(),
                car.getModel(),
                String.valueOf(car.getYear()),
                String.valueOf(car.getBatteryCapacity()),
                car.getLicensePlate(),
                String.valueOf(car.getNumberOfDoors()),
                String.valueOf(car.getSeatingCapacity())
        );
    }
}
