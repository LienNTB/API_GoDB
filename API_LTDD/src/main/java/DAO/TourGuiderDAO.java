package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Tour;
import Model.TourGuider;

public class TourGuiderDAO {
	private Connection connection; // đối tượng kết nối đến cơ sở dữ liệu

	public TourGuiderDAO(Connection connection) {
		this.connection = connection;
	}

	// Phương thức thêm một bản ghi mới vào cơ sở dữ liệu
	public void addTourGuider(TourGuider tourGuider) throws SQLException {
		String query = "INSERT INTO TourGuider (tour_guider_id, name, birthday, gender, address, tel, email, image_link, cic, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, tourGuider.getTourGuiderId());
		stmt.setString(2, tourGuider.getFullName());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			java.util.Date utilDate = dateFormat.parse(tourGuider.getBirthDay()); // Chuyển đổi chuỗi thành kiểu
																					// java.util.Date
			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
		} catch (ParseException e) {
			e.printStackTrace();
		}
		stmt.setDate(3, (java.sql.Date) date);
		stmt.setBoolean(4, tourGuider.isGender());
		stmt.setString(5, tourGuider.getAddress());
		stmt.setString(6, tourGuider.getTel());
		stmt.setString(7, tourGuider.getEmail());
		stmt.setString(8, tourGuider.getImageLink());
		stmt.setString(9, tourGuider.getCic());
		stmt.setBigDecimal(10, tourGuider.getSalary());

		stmt.executeUpdate();
	}

	// Phương thức lấy một bản ghi từ cơ sở dữ liệu dựa trên ID
	public TourGuider getTourGuiderById(String tourGuiderId) throws SQLException {
		String query = "SELECT * FROM TourGuider WHERE tour_guider_id = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, tourGuiderId);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			String fullName = rs.getString("name");
			Date birthDay = rs.getDate("birthday");
			boolean gender = rs.getBoolean("gender");
			String address = rs.getString("address");
			String tel = rs.getString("tel");
			String email = rs.getString("email");
			String imageLink = rs.getString("image_link");
			String cic = rs.getString("cic");
			BigDecimal salary = rs.getBigDecimal("salary");
			return new TourGuider(tourGuiderId, fullName, birthDay, gender, address, tel, email, imageLink, cic,
					salary);

		}
		return null;

	}

	// Phương thức cập nhật một bản ghi trong cơ sở dữ liệu
	public void updateTourGuider(TourGuider tourGuider) throws SQLException {
		String query = "UPDATE TourGuider SET name = ?, birthday = ?, gender = ?, address = ?, tel = ?, email = ?, image_link = ?, cic = ?, salary = ? WHERE tour_guider_id = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, tourGuider.getFullName());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			java.util.Date utilDate = dateFormat.parse(tourGuider.getBirthDay()); // Chuyển đổi chuỗi thành kiểu
																					// java.util.Date
			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
		} catch (ParseException e) {
			e.printStackTrace();
		}
		stmt.setDate(2, (java.sql.Date) date);
		stmt.setBoolean(3, tourGuider.isGender());
		stmt.setString(4, tourGuider.getAddress());
		stmt.setString(5, tourGuider.getTel());
		stmt.setString(6, tourGuider.getEmail());
		stmt.setString(7, tourGuider.getImageLink());
		stmt.setString(8, tourGuider.getCic());
		stmt.setBigDecimal(9, tourGuider.getSalary());
		stmt.setString(10, tourGuider.getTourGuiderId());

	}

	// Phương thức lấy toàn bộ danh sách các bản ghi từ cơ sở dữ liệu
	public List<TourGuider> getAllTourGuiders() throws SQLException {
		List<TourGuider> tourGuiders = new ArrayList<>();
		String query = "SELECT * FROM TourGuider";
		PreparedStatement stmt = connection.prepareStatement(query); 
		ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String tourGuiderId = rs.getString("tour_guider_id");
				String fullName = rs.getString("name");
				Date birthDay = rs.getDate("birthday");
				boolean gender = rs.getBoolean("gender");
				String address = rs.getString("address");
				String tel = rs.getString("tel");
				String email = rs.getString("email");
				String imageLink = rs.getString("image_link");
				String cic = rs.getString("cic");
				BigDecimal salary = rs.getBigDecimal("salary");
				TourGuider tourGuider = new TourGuider(tourGuiderId, fullName, birthDay, gender, address, tel, email,
						imageLink, cic, salary);
				tourGuiders.add(tourGuider);
			}
			return tourGuiders;
		}
}
