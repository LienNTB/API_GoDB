package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Vehicle;

public class VehicleDAO {
    private Connection connection; // đối tượng kết nối đến cơ sở dữ liệu

    public VehicleDAO(Connection connection) {
        this.connection = connection;
    }

    // Phương thức thêm mới một chiếc xe vào cơ sở dữ liệu
    public void addVehicle(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO Vehicle (vehicle_id, rental_company, model, color, license_plate, rental_rate, available) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, vehicle.getVehicleId());
        statement.setString(2, vehicle.getRentalCompany());
        statement.setString(3, vehicle.getModel());
        statement.setString(4, vehicle.getColor());
        statement.setString(5, vehicle.getLicensePlate());
        statement.setBigDecimal(6, vehicle.getRentalRate());
        statement.setBoolean(7, vehicle.isAvailable());
        statement.executeUpdate();
        
    }

    // Phương thức cập nhật thông tin một chiếc xe trong cơ sở dữ liệu
    public void updateVehicle(Vehicle vehicle) throws SQLException {
        String sql = "UPDATE Vehicle " +
                     "SET rental_company = ?, model = ?, color = ?, license_plate = ?, rental_rate = ?, available = ? " +
                     "WHERE vehicle_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, vehicle.getRentalCompany());
        statement.setString(2, vehicle.getModel());
        statement.setString(3, vehicle.getColor());
        statement.setString(4, vehicle.getLicensePlate());
        statement.setBigDecimal(5, vehicle.getRentalRate());
        statement.setBoolean(6, vehicle.isAvailable());
        statement.setString(7, vehicle.getVehicleId());
        statement.executeUpdate();
    
    }

    // Phương thức xóa một chiếc xe khỏi cơ sở dữ liệu
    public void deleteVehicle(Vehicle vehicle) throws SQLException {
        String sql = "DELETE FROM vehicles WHERE vehicle_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, vehicle.getVehicleId());

            statement.executeUpdate();
        }
    }

    // Phương thức lấy danh sách tất cả các chiếc xe trong cơ sở dữ liệu
    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();

        String sql = "SELECT * FROM Vehicle";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Vehicle vehicle = new Vehicle(
            resultSet.getString("vehicle_id"),
            resultSet.getString("rental_company"),
            resultSet.getString("model"),
            resultSet.getString("color"),
            resultSet.getString("license_plate"),
            resultSet.getBigDecimal("rental_rate"),
            resultSet.getBoolean("available")
            );

            vehicles.add(vehicle);
        }
        return vehicles;

 
    }

    // Phương thức lấy một chiếc xe dựa trên id
    public List<Vehicle> getVehicleById(String vehicleId) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>()	;
    	String sql = "SELECT * FROM Vehicle WHERE vehicle_id = ?";

	    PreparedStatement statement = connection.prepareStatement(sql);
    	statement.setString(1, vehicleId);
    	ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Vehicle vehicle = new Vehicle(
            resultSet.getString("vehicle_id"),
            resultSet.getString("rental_company"),
            resultSet.getString("model"),
            resultSet.getString("color"),
            resultSet.getString("license_plate"),
            resultSet.getBigDecimal("rental_rate"),
            resultSet.getBoolean("available")
          );
          vehicles.add(vehicle);
    }
        return vehicles;
    }
}