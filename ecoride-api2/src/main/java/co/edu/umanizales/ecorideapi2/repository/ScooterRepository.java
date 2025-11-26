package co.edu.umanizales.ecorideapi2.repository;

import co.edu.umanizales.ecorideapi2.model.Scooter;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ScooterRepository implements CsvRepository<Scooter> {
    private static final String FILE_PATH = "data/scooters.csv";
    private static final String HEADER = "id,brand,model,year,batteryCapacity,licensePlate,maxSpeed,weight";

    public ScooterRepository() {
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
            throw new RuntimeException("Error initializing scooters CSV file", e);
        }
    }

    @Override
    public List<Scooter> findAll() throws IOException {
        List<Scooter> scooters = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
        
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!line.trim().isEmpty()) {
                scooters.add(parseScooter(line));
            }
        }
        return scooters;
    }

    @Override
    public Optional<Scooter> findById(String id) throws IOException {
        return findAll().stream()
                .filter(scooter -> scooter.getId().equals(id))
                .findFirst();
    }

    @Override
    public Scooter save(Scooter scooter) throws IOException {
        List<Scooter> scooters = findAll();
        scooters.removeIf(s -> s.getId().equals(scooter.getId()));
        scooters.add(scooter);
        saveAll(scooters);
        return scooter;
    }

    @Override
    public void deleteById(String id) throws IOException {
        List<Scooter> scooters = findAll();
        scooters.removeIf(s -> s.getId().equals(id));
        saveAll(scooters);
    }

    @Override
    public void saveAll(List<Scooter> scooters) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(HEADER);
            writer.newLine();
            for (Scooter scooter : scooters) {
                writer.write(toCSV(scooter));
                writer.newLine();
            }
        }
    }

    private Scooter parseScooter(String line) {
        String[] parts = line.split(",");
        return new Scooter(
                parts[0],
                parts[1],
                parts[2],
                Integer.parseInt(parts[3]),
                Double.parseDouble(parts[4]),
                parts[5],
                Double.parseDouble(parts[6]),
                Double.parseDouble(parts[7])
        );
    }

    private String toCSV(Scooter scooter) {
        return String.join(",",
                scooter.getId(),
                scooter.getBrand(),
                scooter.getModel(),
                String.valueOf(scooter.getYear()),
                String.valueOf(scooter.getBatteryCapacity()),
                scooter.getLicensePlate(),
                String.valueOf(scooter.getMaxSpeed()),
                String.valueOf(scooter.getWeight())
        );
    }
}
