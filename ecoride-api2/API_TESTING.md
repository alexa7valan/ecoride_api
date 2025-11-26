# API Testing Guide

## Starting the Application

Run the following command in the project root directory:

```bash
.\mvnw.cmd spring-boot:run
```

The application will start on `http://localhost:8080`

## Testing Endpoints with PowerShell

### 1. Create a Driver

```powershell
$driverBody = @{
    name = "John Doe"
    email = "john@example.com"
    phone = "1234567890"
    licenseNumber = "DL123456"
    experienceYears = 5
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/drivers" -Method Post -Body $driverBody -ContentType "application/json"
```

### 2. Get All Drivers

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/drivers" -Method Get
```

### 3. Create a Car

```powershell
$carBody = @{
    brand = "Tesla"
    model = "Model 3"
    year = 2023
    batteryCapacity = 75.0
    licensePlate = "ABC123"
    numberOfDoors = 4
    seatingCapacity = 5
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/cars" -Method Post -Body $carBody -ContentType "application/json"
```

### 4. Get All Cars

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/cars" -Method Get
```

### 5. Create a Scooter

```powershell
$scooterBody = @{
    brand = "Xiaomi"
    model = "Mi Electric Scooter"
    year = 2023
    batteryCapacity = 12.8
    licensePlate = "SC001"
    maxSpeed = 25.0
    weight = 12.5
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/scooters" -Method Post -Body $scooterBody -ContentType "application/json"
```

### 6. Get All Scooters

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/scooters" -Method Get
```

### 7. Create a Trip

First, get the IDs from the driver and vehicle you created, then:

```powershell
$tripBody = @{
    driverId = "your-driver-id-here"
    vehicleId = "your-vehicle-id-here"
    distance = 25.5
    startTime = "2025-10-24T08:00:00"
    endTime = "2025-10-24T09:30:00"
    origin = "Downtown"
    destination = "Airport"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/trips" -Method Post -Body $tripBody -ContentType "application/json"
```

### 8. Get All Trips

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/trips" -Method Get
```

### 9. Update a Driver

```powershell
$updateBody = @{
    name = "John Doe Updated"
    email = "john.updated@example.com"
    phone = "9876543210"
    licenseNumber = "DL123456"
    experienceYears = 7
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/drivers/your-driver-id" -Method Put -Body $updateBody -ContentType "application/json"
```

### 10. Delete a Driver

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/drivers/your-driver-id" -Method Delete
```

## Testing with cURL (if available)

### Create a Driver
```bash
curl -X POST http://localhost:8080/api/drivers -H "Content-Type: application/json" -d "{\"name\":\"John Doe\",\"email\":\"john@example.com\",\"phone\":\"1234567890\",\"licenseNumber\":\"DL123456\",\"experienceYears\":5}"
```

### Get All Drivers
```bash
curl http://localhost:8080/api/drivers
```

## Verifying Data Persistence

After creating some data, check the `data/` directory in your project root. You should see CSV files:
- `data/drivers.csv`
- `data/cars.csv`
- `data/scooters.csv`
- `data/trips.csv`

These files contain all the persisted data in CSV format.
