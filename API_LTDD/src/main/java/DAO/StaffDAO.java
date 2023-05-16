package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import java.util.ArrayList;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import DBConnect.DBConnect;
import Model.Staff;

public class StaffDAO {
	private Connection connection; // đối tượng kết nối đến cơ sở dữ liệu

	public StaffDAO(Connection connection) {
		this.connection = connection;
	}
	// Phương thức lấy một nhân viên dựa trên id
	public Staff getStaffById(String staffId) throws SQLException {
		String sql = "SELECT * FROM Staff WHERE customer_id=?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, staffId);
		ResultSet resultSet = stmt.executeQuery();

		while (resultSet.next()) {

			String full_name = resultSet.getString("full_name");
			String email = resultSet.getString("email");
			String phone_number = resultSet.getString("phone_number");
			boolean gender = resultSet.getBoolean("gender");
			Date birth_day = resultSet.getDate("birth_day");
			String cic = resultSet.getString("cic");
			String address = resultSet.getString("staff_address");
			String imageLink = resultSet.getString("image_link");

			Staff staff = new Staff(staffId, full_name, email, phone_number, gender, birth_day, cic, address,
					imageLink);
			return staff;

		}
		return null;

	}
	
	// Phương thức lấy danh sách tất cả các nhân viên trong cơ sở dữ liệu
	public List<Staff> getAllStaffs() throws SQLException {
		List<Staff> staffs = new ArrayList<>();

		String sql = "SELECT * FROM Staff";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			String staff_id = resultSet.getString("staff_Id");
			String full_name = resultSet.getString("full_name");
			String email = resultSet.getString("email");
			String phone_number = resultSet.getString("phone_number");
			boolean gender = resultSet.getBoolean("gender");
			Date birth_day = resultSet.getDate("birth_day");
			String cic = resultSet.getString("cic");
			String address = resultSet.getString("staff_address");
			String imageLink = resultSet.getString("image_link");

			Staff staff = new Staff(staff_id, full_name, email, phone_number, gender, birth_day, cic, address,
					imageLink);
			staffs.add(staff);
		}

		return staffs;
	}


	
	// Phương thức thêm mới một nhân viên vào cơ sở dữ liệu
	public void addStaff(Staff staff) throws SQLException {
		String sql = "INSERT INTO staff (staff_id, full_name, email, phone_number, gender, birth_day, cic, address, image_link) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, staff.getStaffId());
		statement.setString(2, staff.getFullName());
		statement.setString(3, staff.getEmail());
		statement.setString(4, staff.getPhoneNumber());
		statement.setBoolean(5, staff.isGender());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			java.util.Date utilDate = dateFormat.parse(staff.getBirthDay()); // Chuyển đổi chuỗi thành kiểu
																				// java.util.Date
			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
		} catch (ParseException e) {
			e.printStackTrace();
		}
		statement.setDate(6, date);
		statement.setString(7, staff.getCic());
		statement.setString(8, staff.getAddress());
		statement.setString(9, staff.getImageLink());

		statement.executeUpdate();

	}

	// Phương thức cập nhật thông tin một nhân viên trong cơ sở dữ liệu
	public void updateStaff(Staff staff) throws SQLException {
		String sql = "UPDATE staffs "
				+ "SET full_name = ?, email = ?, phone_number = ?, gender = ?, birth_day = ?, cic = ?, address = ?, image_link = ? "
				+ "WHERE staff_id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, staff.getFullName());
		statement.setString(2, staff.getEmail());
		statement.setString(3, staff.getPhoneNumber());
		statement.setBoolean(4, staff.isGender());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			java.util.Date utilDate = dateFormat.parse(staff.getBirthDay()); // Chuyển đổi chuỗi thành kiểu
																				// java.util.Date
			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
		} catch (ParseException e) {
			e.printStackTrace();
		}
		statement.setDate(5, date);
		statement.setString(6, staff.getCic());
		statement.setString(7, staff.getAddress());
		statement.setString(8, staff.getImageLink());
		statement.setString(9, staff.getStaffId());

		statement.executeUpdate();

	}


}