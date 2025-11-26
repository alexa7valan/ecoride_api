package co.edu.umanizales.ecorideapi2.repository;

import co.edu.umanizales.ecorideapi2.model.Driver;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DriverRepository implements CsvRepository<Driver> {
    private static final String FILE_PATH = "data/drivers.csv";
    private static final String HEADER = "id,name,email,phone,licenseNumber,experienceYears";

    public DriverRepository() {
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
            throw new RuntimeException("Error initializing drivers CSV file", e);
        }
    }

    @Override
    public List<Driver> findAll() throws IOException {
        List<Driver> drivers = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
        
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!line.trim().isEmpty()) {
                drivers.add(parseDriver(line));
            }
        }
        return drivers;
    }

    @Override
    public Optional<Driver> findById(String id) throws IOException {
        return findAll().stream()
                .filter(driver -> driver.getId().equals(id))
                .findFirst();
    }

    @Override
    public Driver save(Driver driver) throws IOException {
        List<Driver> drivers = findAll();
        drivers.removeIf(d -> d.getId().equals(driver.getId()));
        drivers.add(driver);
        saveAll(drivers);
        return driver;
    }

    @Override
    public void deleteById(String id) throws IOException {
        List<Driver> drivers = findAll();
        drivers.removeIf(d -> d.getId().equals(id));
        saveAll(drivers);
    }

    @Override
    public void saveAll(List<Driver> drivers) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(HEADER);
            writer.newLine();
            for (Driver driver : drivers) {
                writer.write(toCSV(driver));
                writer.newLine();
            }
        }
    }

    private Driver parseDriver(String line) {
        String[] parts = line.split(",");
        return new Driver(
                parts[0],
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                Integer.parseInt(parts[5])
        );
    }

    private String toCSV(Driver driver) {
        return String.join(",",
                driver.getId(),
                driver.getName(),
                driver.getEmail(),
                driver.getPhone(),
                driver.getLicenseNumber(),
                String.valueOf(driver.getExperienceYears())
        );
    }
}
