package co.edu.umanizales.ecorideapi2.repository;

import co.edu.umanizales.ecorideapi2.model.Trip;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TripRepository implements CsvRepository<Trip> {
    private static final String FILE_PATH = "data/trips.csv";
    private static final String HEADER = "id,driverId,vehicleId,distance,startTime,endTime,origin,destination";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public TripRepository() {
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
            throw new RuntimeException("Error initializing trips CSV file", e);
        }
    }

    @Override
    public List<Trip> findAll() throws IOException {
        List<Trip> trips = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
        
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!line.trim().isEmpty()) {
                trips.add(parseTrip(line));
            }
        }
        return trips;
    }

    @Override
    public Optional<Trip> findById(String id) throws IOException {
        return findAll().stream()
                .filter(trip -> trip.getId().equals(id))
                .findFirst();
    }

    @Override
    public Trip save(Trip trip) throws IOException {
        List<Trip> trips = findAll();
        trips.removeIf(t -> t.getId().equals(trip.getId()));
        trips.add(trip);
        saveAll(trips);
        return trip;
    }

    @Override
    public void deleteById(String id) throws IOException {
        List<Trip> trips = findAll();
        trips.removeIf(t -> t.getId().equals(id));
        saveAll(trips);
    }

    @Override
    public void saveAll(List<Trip> trips) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(HEADER);
            writer.newLine();
            for (Trip trip : trips) {
                writer.write(toCSV(trip));
                writer.newLine();
            }
        }
    }

    private Trip parseTrip(String line) {
        String[] parts = line.split(",");
        return new Trip(
                parts[0],
                parts[1],
                parts[2],
                Double.parseDouble(parts[3]),
                LocalDateTime.parse(parts[4], FORMATTER),
                LocalDateTime.parse(parts[5], FORMATTER),
                parts[6],
                parts[7]
        );
    }

    private String toCSV(Trip trip) {
        return String.join(",",
                trip.getId(),
                trip.getDriverId(),
                trip.getVehicleId(),
                String.valueOf(trip.getDistance()),
                trip.getStartTime().format(FORMATTER),
                trip.getEndTime().format(FORMATTER),
                trip.getOrigin(),
                trip.getDestination()
        );
    }
}
