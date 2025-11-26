# EcoRide API

A REST API built with Spring Boot for managing electric vehicle rides, drivers, and vehicles.

## Features

- **MVC Architecture**: Clean separation of concerns with Model, View (REST Controllers), and Service layers
- **SOLID Principles**: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, and Dependency Inversion
- **Inheritance Hierarchy**: 
  - `Person` base class with `Driver` and `Passenger` subclasses
  - `Vehicle` base class with `Car` and `Scooter` subclasses
- **CSV Persistence**: All data is stored in CSV files for easy portability

## Architecture

```
├── model/          # Domain entities (Person, Driver, Passenger, Vehicle, Car, Scooter, Trip)
├── dto/            # Data Transfer Objects for API requests/responses
├── repository/     # CSV-based persistence layer
├── service/        # Business logic layer
└── controller/     # REST API endpoints
```

## API Endpoints

### Drivers
- `GET /api/drivers` - Get all drivers
- `GET /api/drivers/{id}` - Get driver by ID
- `POST /api/drivers` - Create new driver
- `PUT /api/drivers/{id}` - Update driver
- `DELETE /api/drivers/{id}` - Delete driver

### Cars
- `GET /api/cars` - Get all cars
- `GET /api/cars/{id}` - Get car by ID
- `POST /api/cars` - Create new car
- `PUT /api/cars/{id}` - Update car
- `DELETE /api/cars/{id}` - Delete car

### Scooters
- `GET /api/scooters` - Get all scooters
- `GET /api/scooters/{id}` - Get scooter by ID
- `POST /api/scooters` - Create new scooter
- `PUT /api/scooters/{id}` - Update scooter
- `DELETE /api/scooters/{id}` - Delete scooter

### Trips
- `GET /api/trips` - Get all trips
- `GET /api/trips/{id}` - Get trip by ID
- `POST /api/trips` - Create new trip
- `PUT /api/trips/{id}` - Update trip
- `DELETE /api/trips/{id}` - Delete trip

## Running the Application

1. Make sure you have Java 17 or higher installed
2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or on Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```
3. The API will be available at `http://localhost:8080`

## Example Requests

### Create a Driver
```bash
curl -X POST http://localhost:8080/api/drivers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "1234567890",
    "licenseNumber": "DL123456",
    "experienceYears": 5
  }'
```

### Create a Car
```bash
curl -X POST http://localhost:8080/api/cars \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Tesla",
    "model": "Model 3",
    "year": 2023,
    "batteryCapacity": 75.0,
    "licensePlate": "ABC123",
    "numberOfDoors": 4,
    "seatingCapacity": 5
  }'
```

### Create a Trip
```bash
curl -X POST http://localhost:8080/api/trips \
  -H "Content-Type: application/json" \
  -d '{
    "driverId": "driver-id-here",
    "vehicleId": "vehicle-id-here",
    "distance": 25.5,
    "startTime": "2025-10-24T08:00:00",
    "endTime": "2025-10-24T09:30:00",
    "origin": "Downtown",
    "destination": "Airport"
  }'
```

## Data Storage

All data is stored in CSV files in the `data/` directory:
- `data/drivers.csv` - Driver information
- `data/cars.csv` - Car information
- `data/scooters.csv` - Scooter information
- `data/trips.csv` - Trip records

## Technologies Used

- Java 17
- Spring Boot 3.5.7
- Lombok
- Maven
