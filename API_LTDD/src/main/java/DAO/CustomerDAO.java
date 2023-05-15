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
import Model.Customer;

public class CustomerDAO {
	private Connection conn;

	public CustomerDAO(Connection conn) {
		this.conn = conn;
	}

	// Thêm một khách hàng mới vào cơ sở dữ liệu
	public void addCustomer(Customer customer) throws SQLException {
		String sql = "INSERT INTO Customer (customer_id, full_name, email, phone_number, image_link, cus_address, gender, birth_day) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, customer.getCustomerId());
		stmt.setString(2, customer.getFullName());
		stmt.setString(3, customer.getEmail());
		stmt.setString(4, customer.getPhoneNumber());
		stmt.setString(5, customer.getImageLink());
		stmt.setString(6, customer.getAddress());
		stmt.setBoolean(7, customer.isGender());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			java.util.Date utilDate = dateFormat.parse(customer.getBirthDay()); // Chuyển đổi chuỗi thành kiểu
																				// java.util.Date
			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
		} catch (ParseException e) {
			e.printStackTrace();
		}

		stmt.setDate(8, date);
		stmt.executeUpdate();
	}

	// Lấy danh sách tất cả khách hàng
	public List<Customer> getAllCustomers() throws SQLException {
		List<Customer> customerList = new ArrayList<>();
		String sql = "SELECT * FROM Customer";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			String customer_id = rs.getString("customer_id");
			String full_name = rs.getString("full_name");
			String email = rs.getString("email");
			String phone_number = rs.getString("phone_number");
			String image_link = rs.getString("image_link");
			String cus_address = rs.getString("cus_address");
			boolean gender = rs.getBoolean("gender");
			Date birthDay = rs.getDate("birth_day");
			Customer customer = new Customer(customer_id, full_name, email, phone_number, image_link, cus_address,
					gender, birthDay);
			customerList.add(customer);
		}

		return customerList;
	}

	// Lấy khách hàng theo customer_id
	public Customer getCustomerById(String customer_id) throws SQLException {
		String sql = "SELECT * FROM Customer WHERE customer_id=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, customer_id);
		ResultSet rs = stmt.executeQuery();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (rs.next()) {
			String full_name = rs.getString("full_name");
			String email = rs.getString("email");
			String phone_number = rs.getString("phone_number");
			String image_link = rs.getString("image_link");
			String cus_address = rs.getString("cus_address");
			boolean gender = rs.getBoolean("gender");
			 Date bDate = rs.getDate("birth_day");
//		     java.util.Date birthDay = new java.util.Date(bDate.getTime());
		        Customer customer = new Customer(customer_id, full_name, email, phone_number, image_link, cus_address,
		                gender, bDate);
		        return customer;
		}

		return null;
	}

	// Cập nhật thông tin khách hàng
	public void updateCustomer(Customer customer) throws SQLException {
		String sql = "UPDATE Customer SET full_name=?, email=?, phone_number=?, image_link=?, cus_address=?, gender=?, birth_day=? WHERE customer_id=?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, customer.getFullName());
		stmt.setString(2, customer.getEmail());
		stmt.setString(3, customer.getPhoneNumber());
		stmt.setString(4, customer.getImageLink());
		stmt.setString(5, customer.getAddress());
		stmt.setBoolean(6, customer.isGender());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			java.util.Date utilDate = dateFormat.parse(customer.getBirthDay()); // Chuyển đổi chuỗi thành kiểu
																				// java.util.Date
			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
		} catch (ParseException e) {
			e.printStackTrace();
		}
		stmt.setDate(7, date);

		stmt.setString(8, customer.getCustomerId());
		stmt.executeUpdate();
	}

	// Xóa khách hàng theo customer_id
	public void deleteCustomer(Customer customer) throws SQLException {
		
		// phần này sửa lại chỉ thay đổi status thôi chứ k xóa
		String sql = " FROM customers WHERE customer_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, customer.getCustomerId());
		stmt.executeUpdate();
	}
}
