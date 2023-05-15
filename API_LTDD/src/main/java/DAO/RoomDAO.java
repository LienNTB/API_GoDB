package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Room;

public class RoomDAO {
    private Connection connection;

    public RoomDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm đối tượng Room vào database
    public void addRoom(Room room) throws SQLException {
        String query = "INSERT INTO Room (room_id, hotel_id, room_number, room_type, rental_rate, room_capacity, room_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, room.getRoomId());
        statement.setString(2, room.getHotelId());
        statement.setString(3, room.getRoomNumber());
        statement.setString(4, room.getRoomType());
        statement.setBigDecimal(5, room.getRentalRate());
        statement.setInt(6, room.getRoomCapacity());
        statement.setString(7, room.getRoomStatus());
        statement.executeUpdate();
    }

    // Lấy danh sách đối tượng Room từ database
    public List<Room> getRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM Room";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Room room = new Room(
                resultSet.getInt("room_id"),
                resultSet.getString("hotel_id"),
                resultSet.getString("room_number"),
                resultSet.getString("room_type"),
                resultSet.getBigDecimal("rental_rate"),
                resultSet.getInt("room_capacity"),
                resultSet.getString("room_status")
            );
            rooms.add(room);
        }
        return rooms;
    }

    // Lấy đối tượng Room từ database theo ID
    public Room getRoomById(int roomId) throws SQLException {
        String query = "SELECT * FROM Room WHERE room_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, roomId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Room room = new Room(
                resultSet.getInt("room_id"),
                resultSet.getString("hotel_id"),
                resultSet.getString("room_number"),
                resultSet.getString("room_type"),
                resultSet.getBigDecimal("rental_rate"),
                resultSet.getInt("room_capacity"),
                resultSet.getString("room_status")
            );
            return room;
        } else {
            return null;
        }
    }

    // Lấy danh sách đối tượng Room từ database theo status
    public List<Room> getRoomsByStatus(String roomStatus) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM Room WHERE room_status = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, roomStatus);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Room room = new Room(
                resultSet.getInt("room_id"),
                resultSet.getString("hotel_id"),
                resultSet.getString("room_number"),
                resultSet.getString("room_type"),
                resultSet.getBigDecimal("rental_rate"),
                resultSet.getInt("room_capacity"),
                resultSet.getString("room_status")
            );
            rooms.add(room);
        }
        return rooms;
    }
    
    // Cập nhật đối tượng Room trong database
    public void updateRoom(Room room) throws SQLException {
        String query = "UPDATE Room SET hotel_id = ?, room_number = ?, room_type = ?, rental_rate = ?, room_capacity = ?, room_status = ? WHERE room_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, room.getHotelId());
        statement.setString(2, room.getRoomNumber());
        statement.setString(3, room.getRoomType());
        statement.setBigDecimal(4, room.getRentalRate());
        statement.setInt(5, room.getRoomCapacity());
        statement.setString(6, room.getRoomStatus());
        statement.setInt(7, room.getRoomId());
        statement.executeUpdate();
    }

    // Xóa đối tượng Room trong database theo ID
    public void deleteRoom(int roomId) throws SQLException {
        String query = "DELETE FROM Room WHERE room_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, roomId);
        statement.executeUpdate();
    }
}
