package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import Model.Booking;
import Model.Customer;
public class BookingDAO {

	private Connection conn;

    public BookingDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm mới một bản ghi booking vào CSDL
    public void addBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO Booking (booking_id, customer_id, staff_id, vehicle_id, tour_id, hotel_id, booking_date, booking_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, booking.getBookingId());
            stmt.setString(2, booking.getCustomerId());
            stmt.setString(3, booking.getStaffId());
            stmt.setString(4, booking.getVehicleId());
            stmt.setString(5, booking.getTourId());
            stmt.setString(6, booking.getHotelId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
    		try {
    			java.util.Date utilDate = dateFormat.parse(booking.getBookingDate()); // Chuyển đổi chuỗi thành kiểu
    																					// java.util.Date
    			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}

            stmt.setDate(7, date);
            stmt.setString(8, booking.getBookingType());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
    public List<Booking> getAllBookings() throws SQLException {
		List<Booking> bookingList = new ArrayList<>();
		String sql = "SELECT * FROM Booking";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			String booking_id = rs.getString("booking_id");
			String customer_id = rs.getString("customer_id");
			String staff_id = rs.getString("staff_id");
			String vehicle_id = rs.getString("vehicle_id");
			String tour_id = rs.getString("tour_id");
			String hotel_id = rs.getString("hotel_id");
			String booking_type = rs.getString("booking_type");
			Date booking_date = rs.getDate("booking_date");
			Booking booking = new Booking(booking_id, customer_id, staff_id, vehicle_id, tour_id, hotel_id,
					booking_date, booking_type);
			bookingList.add(booking);
		}

		return bookingList;
	}

    // Lấy danh sách các bản ghi booking theo booking_type
    public List<Booking> getBookingsByType(String bookingType) throws SQLException {
        String sql = "SELECT * FROM Booking WHERE booking_type = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bookingType);

            ResultSet rs = stmt.executeQuery();

            List<Booking> bookings = new ArrayList<>();

            while (rs.next()) {
            	String booking_id = rs.getString("booking_id");
    			String customer_id = rs.getString("customer_id");
    			String staff_id = rs.getString("staff_id");
    			String vehicle_id = rs.getString("vehicle_id");
    			String tour_id = rs.getString("tour_id");
    			String hotel_id = rs.getString("hotel_id");
    			Date booking_date = rs.getDate("booking_date");
    			Booking booking = new Booking(booking_id, customer_id, staff_id, vehicle_id, tour_id, hotel_id,
    					booking_date, bookingType);

                bookings.add(booking);
            }

            return bookings;
        } catch (SQLException e) {
            throw e;
        }
    }

    // Cập nhật thông tin một bản ghi booking
    public void updateBooking(Booking booking) throws SQLException {
        String sql = "UPDATE Booking SET customer_id = ?, staff_id = ?, vehicle_id = ?, tour_id = ?, hotel_id = ?, booking_date = ?, booking_type = ? WHERE booking_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, booking.getCustomerId());
            stmt.setString(2, booking.getStaffId());
            stmt.setString(3, booking.getVehicleId());
            stmt.setString(4, booking.getTourId());
            stmt.setString(5, booking.getHotelId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    		Date date = null;
    		try {
    			java.util.Date utilDate = dateFormat.parse(booking.getBookingDate()); // Chuyển đổi chuỗi thành kiểu
    																				// java.util.Date
    			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}

    		stmt.setDate(6, date);
            stmt.setString(7, booking.getBookingType());
            stmt.setString(8, booking.getBookingId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    // Xóa một bản ghi booking theo booking_id
    public void deleteBooking(String bookingId) throws SQLException {
        String sql = "DELETE FROM Booking WHERE booking_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bookingId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
}
