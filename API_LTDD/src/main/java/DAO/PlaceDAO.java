package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Place;

public class PlaceDAO {
    private Connection connection;

    public PlaceDAO(Connection connection) {
        this.connection = connection;
    }
    
	public boolean CheckPlace(String placeId) throws SQLException {
		String query = "select * from Place where place_id = ?";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, placeId);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;
	}

    // Thêm đối tượng Place vào database
    public void addPlace(Place place) throws SQLException {
        String query = "INSERT INTO Place (place_id, place_name, dec, contents, image_link) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, place.getPlaceId());
        statement.setString(2, place.getPlaceName());
        statement.setString(3, place.getDec());
        statement.setString(4, place.getContents());
        statement.setString(5, place.getImageLink());
        statement.executeUpdate();
    }

    // Lấy danh sách đối tượng Place từ database
    public List<Place> getPlaces() throws SQLException {
        List<Place> places = new ArrayList<>();
        String query = "SELECT * FROM Place";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Place place = new Place(
                resultSet.getString("place_id"),
                resultSet.getString("place_name"),
                resultSet.getString("dec"),
                resultSet.getString("contents"),
                resultSet.getString("image_link")
            );
            places.add(place);
        }
        return places;
    }

    // Lấy đối tượng Place từ database theo ID
    public Place getPlaceById(String placeId) throws SQLException {
        String query = "SELECT * FROM Place WHERE place_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, placeId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Place place = new Place(
                resultSet.getString("place_id"),
                resultSet.getString("place_name"),
                resultSet.getString("dec"),
                resultSet.getString("contents"),
                resultSet.getString("image_link")
            );
            return place;
        } else {
            return null;
        }
    }

    // Cập nhật đối tượng Place trong database
    public void updatePlace(Place place) throws SQLException {
        String query = "UPDATE Place SET place_name = ?, dec = ?, contents = ?, image_link = ? WHERE place_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, place.getPlaceName());
        statement.setString(2, place.getDec());
        statement.setString(3, place.getContents());
        statement.setString(4, place.getImageLink());
        statement.setString(5, place.getPlaceId());
        statement.executeUpdate();
    }

    // Xóa đối tượng Place trong database theo ID
    public void deletePlace(String placeId) throws SQLException {
        String query = "DELETE FROM Place WHERE place_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, placeId);
        statement.executeUpdate();
    }
}
