package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Model.Tour;

public class TourDAO {
  // đối tượng kết nối đến cơ sở dữ liệu

	private Connection conn;

	public TourDAO(Connection conn) {
		this.conn = conn;
	}
    // Phương thức thêm một bản ghi mới vào cơ sở dữ liệu
    public void addTour(Tour tour) throws SQLException {
        String query = "INSERT INTO Tour (tour_id, tour_guider_id, place_id, tour_name, place_go, date_go, date_back, num_person, price, image_link, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, tour.getTourId());
		statement.setString(2, tour.getTourGuiderId());
		statement.setString(3, tour.getPlaceId());
		statement.setString(4, tour.getTourName());
		statement.setString(5, tour.getPlaceGo());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			java.util.Date utilDate = dateFormat.parse(tour.getDateGo()); // Chuyển đổi chuỗi thành kiểu
																				// java.util.Date
			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
		} catch (ParseException e) {
			e.printStackTrace();
		}
		statement.setDate(6, date);
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = null;
		try {
			java.util.Date utilDate = dateFormat1.parse(tour.getDateBack()); // Chuyển đổi chuỗi thành kiểu
																				// java.util.Date
			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
		} catch (ParseException e) {
			e.printStackTrace();
		}
		statement.setInt(7, tour.getNumPerson());
		statement.setBigDecimal(8, tour.getPrice());
		statement.setString(9, tour.getImageLink());
		statement.setBoolean(10,tour.isState());

		statement.executeUpdate();
    }

    // Phương thức lấy một bản ghi từ cơ sở dữ liệu dựa trên ID
    public Tour getTourById(String tourId) throws SQLException{
        String query = "SELECT * FROM Tour WHERE tour_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tourId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tourGuiderId = rs.getString("tour_guider_id");
                    String placeId = rs.getString("place_id");
                    String tourName = rs.getString("tour_name");
                    String placeGo = rs.getString("place_go");
                    Date dateGo = rs.getDate("date_go");
                    Date dateBack = rs.getDate("date_back");
                    int numPerson = rs.getInt("num_person");
                    BigDecimal price = rs.getBigDecimal("price");
                    String imageLink = rs.getString("image_link");
                    boolean state = rs.getBoolean("state");
                    return new Tour(tourId, tourGuiderId, placeId, tourName, placeGo, dateGo, dateBack, numPerson, price, imageLink, state);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Phương thức cập nhật một bản ghi trong cơ sở dữ liệu
    public void updateTour(Tour tour) throws SQLException{
        String query = "UPDATE Tour SET tour_guider_id = ?, place_id = ?, tour_name = ?, place_go = ?, date_go = ?, date_back = ?, num_person = ?, price = ?, image_link = ?, state = ? WHERE tour_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, tour.getTourGuiderId());
            stmt.setString(2, tour.getPlaceId());
            stmt.setString(3, tour.getTourName());
            stmt.setString(4, tour.getPlaceGo());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    		Date date = null;
    		try {
    			java.util.Date utilDate = dateFormat.parse(tour.getDateGo()); // Chuyển đổi chuỗi thành kiểu
    																				// java.util.Date
    			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
            stmt.setDate(6,(java.sql.Date) date);
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
    		Date date1 = null;
    		try {
    			java.util.Date utilDate = dateFormat1.parse(tour.getDateBack()); // Chuyển đổi chuỗi thành kiểu
    																				// java.util.Date
    			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
            stmt.setDate(7,(java.sql.Date) date1);
            stmt.setInt(7, tour.getNumPerson());
            stmt.setBigDecimal(8, tour.getPrice());
            stmt.setString(9, tour.getImageLink());
            stmt.setBoolean(10, tour.isState());
            
    		stmt.executeUpdate();
		
    }

    // Phương thức xóa một bản ghi từ cơ sở dữ liệu dựa trên ID
	public void deleteTour(Tour tour) throws SQLException {
		String sql = "DELETE FROM Tour WHERE tour_id = ?";

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, tour.getTourId());

		statement.executeUpdate();

	}

    // Phương thức lấy tất cả bản ghi từ cơ sở dữ liệu
    public List<Tour> getAllTours()throws SQLException {
        String query = "SELECT * FROM Tour";
        List<Tour> tours = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement(query);
        try ( ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String tourId = rs.getString("tour_id");
                String tourGuiderId = rs.getString("tour_guider_id");
                String placeId = rs.getString("place_id");
                String tourName = rs.getString("tour_name");
                String placeGo = rs.getString("place_go");
                Date dateGo = rs.getDate("date_go");
                Date dateBack = rs.getDate("date_back");
                int numPerson = rs.getInt("num_person");
                BigDecimal price = rs.getBigDecimal("price");
                String imageLink = rs.getString("image_link");
                boolean state = rs.getBoolean("state");
                Tour tour = new Tour(tourId, tourGuiderId, placeId, tourName, placeGo, dateGo, dateBack, numPerson, price, imageLink, state);
                tours.add(tour);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tours;
    }

    
}