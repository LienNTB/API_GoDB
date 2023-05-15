package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import Model.Hotel;

public class HotelDAO {
	private Connection conn;
	 public HotelDAO(Connection connection) {
	        this.conn = connection;
	 }
	 public void addHotel(Hotel hotel) throws SQLException {
	        String sql = "INSERT INTO Hotel (hotel_id, hotel_name, hotel_address, province, phone_number, email, website, image_link)" +" VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setString(1, hotel.getHotelId());
	        statement.setString(2, hotel.getHotelName());
	        statement.setString(3, hotel.getHotelAddress());
	        statement.setString(4, hotel.getProvince());
	        statement.setString(5, hotel.getPhoneNumber());
	        statement.setString(6, hotel.getEmail());
	        statement.setString(7, hotel.getWebsite());
	        statement.setString(8, hotel.getImageLink());
	        statement.executeUpdate();
	    }
	 public void updateHotel(Hotel hotel) throws SQLException {
	        String sql = "UPDATE Hotel SET hotel_name=?, hotel_address=?, province=?, phone_number=?, email=?, website=?, image_link=?" +" WHERE hotel_id=?";
	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setString(1, hotel.getHotelName());
	        statement.setString(2, hotel.getHotelAddress());
	        statement.setString(3, hotel.getProvince());
	        statement.setString(4, hotel.getPhoneNumber());
	        statement.setString(5, hotel.getEmail());
	        statement.setString(6, hotel.getWebsite());
	        statement.setString(7, hotel.getImageLink());
	        statement.setString(8, hotel.getHotelId());
	        statement.executeUpdate();
	    }
	 public void deleteHotel(Hotel hotel) throws SQLException {
	        String sql = "DELETE FROM Hotel WHERE hotel_id=?";
	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setString(1, hotel.getHotelId());
	        statement.executeUpdate();
	    }

	    public Hotel getHotelById(String hotel_id) throws SQLException {
	        String sql = "SELECT * FROM Hotel WHERE hotel_id=?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    	stmt.setString(1, hotel_id);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) { 
		           String hotel_name = rs.getString("hotel_name");
		           String hotel_address = rs.getString("hotel_address"); 
		           String province = rs.getString("province");
		           String phone_number = rs.getString("phone_number"); 
		           String email = rs.getString("email");	                    
		           String website = rs.getString("website"); 
		           String image_link  = rs.getString("image_link");
		           Hotel hotel = new Hotel(hotel_id,hotel_name,hotel_address,province,phone_number,email,website,image_link);
		           return hotel;
	        }
	        return null;
	    }

	    public List<Hotel> getAllHotels() throws SQLException {
	        List<Hotel> hotellist = new ArrayList<>();
	        String sql = "SELECT * FROM Hotel";
	        PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	           String hotel_id =rs.getString("hotel_id"); 
	           String hotel_name = rs.getString("hotel_name");
	           String hotel_address = rs.getString("hotel_address"); 
	           String province = rs.getString("province");
	           String phone_number = rs.getString("phone_number"); 
	           String email = rs.getString("email");	                    
	           String website = rs.getString("website"); 
	           String image_link  = rs.getString("image_link");
	           Hotel hotel = new Hotel(hotel_id,hotel_name,hotel_address,province,phone_number,email,website,image_link);
	           hotellist.add(hotel);
	        }
	        return hotellist;
	    }

	    public List<Hotel> searchHotels(String keyword) throws SQLException {
	        List<Hotel> hotels = new ArrayList<>();
	        String sql = "SELECT * FROM Hotel WHERE hotel_name LIKE ? OR hotel_address LIKE ? OR province LIKE ?";
	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setString(1, "%" + keyword + "%");
	        statement.setString(2, "%" + keyword + "%");
	        statement.setString(3, "%" + keyword + "%");
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	        	  String hotel_id =rs.getString("hotel_id"); 
		           String hotel_name = rs.getString("hotel_name");
		           String hotel_address = rs.getString("hotel_address"); 
		           String province = rs.getString("province");
		           String phone_number = rs.getString("phone_number"); 
		           String email = rs.getString("email");	                    
		           String website = rs.getString("website"); 
		           String image_link  = rs.getString("image_link");
		           Hotel hotel = new Hotel(hotel_id,hotel_name,hotel_address,province,phone_number,email,website,image_link);
	            hotels.add(hotel);
	        }
	        return hotels;
	    }
}
