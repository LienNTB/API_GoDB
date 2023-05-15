package Model;

import java.math.BigDecimal;

public class Vehicle {
    private String vehicleId;
    private String rentalCompany;
    private String model;
    private String color;
    private String licensePlate;
    private BigDecimal rentalRate;
    private boolean available;

    public Vehicle(String vehicleId, String rentalCompany, String model, String color, String licensePlate, BigDecimal rentalRate, boolean available) {
        this.vehicleId = vehicleId;
        this.rentalCompany = rentalCompany;
        this.model = model;
        this.color = color;
        this.licensePlate = licensePlate;
        this.rentalRate = rentalRate;
        this.available = available;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getRentalCompany() {
        return rentalCompany;
    }

    public void setRentalCompany(String rentalCompany) {
        this.rentalCompany = rentalCompany;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
