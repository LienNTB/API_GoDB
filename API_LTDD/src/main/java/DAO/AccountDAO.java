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

import DBConnect.DBConnect;
import Model.Account;
import Model.Customer;
import Model.Staff;
public class AccountDAO {
	private Connection conn;

    public AccountDAO(Connection conn) {
        this.conn = conn;
    }
	   public List<Account> getAllAccounts() throws SQLException {
	    	List<Account> accountList = new ArrayList<>();
	    	String sql = "SELECT * FROM Account";
	    	PreparedStatement stmt = conn.prepareStatement(sql);
	    	ResultSet rs = stmt.executeQuery();
	    	
	    	while (rs.next()) {
	    		String account_id = rs.getString("account_id");
	    		String username = rs.getString("username");
	    		String account_type = rs.getString("account_type");
	    		String staff_id = rs.getString("staff_id");
	    		String customer_id = rs.getString("customer_id");
	    		boolean acc_status=rs.getBoolean("acc_status");
	    		String acc_password=rs.getString("acc_password");
	    		Account account = new Account(account_id, username, account_type, staff_id, customer_id, acc_status,acc_password);
	    		accountList.add(account);
	    	}
	    	
	    	return accountList;
	    }

	   public void addAccount(Account account) throws SQLException {
		   String sql = "INSERT INTO Account (account_id, username, account_type, staff_id, customer_id, acc_status, acc_password) VALUES (?, ?, ?, ?, ?, ?, ?)";
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setString(1, account.getAccountId());
		        stmt.setString(2, account.getUsername());
		        stmt.setString(3, account.getAccountType());
		        stmt.setString(4, account.getStaffId());
		        stmt.setString(5, account.getCustomerId());
		        stmt.setBoolean(6, account.isAcc_status());
		        stmt.setString(7, account.getAcc_password());
		        stmt.executeUpdate();
		    } catch (SQLException e) {
		        throw e;
		    }
	   }
	    public Account getAccountById(String account_id) throws SQLException {
	    	String sql =  "SELECT * FROM Account WHERE account_id=?";
	    	PreparedStatement stmt = conn.prepareStatement(sql);
	    	stmt.setString(1, account_id);
	    	ResultSet rs = stmt.executeQuery();
	    	
	    	if (rs.next()) {
	    		String username = rs.getString("username");
	    		String account_type = rs.getString("account_type");
	    		String customer_id = rs.getString("customer_id");
	    		String staff_id = rs.getString("staff_id");
	    		Boolean acc_status = rs.getBoolean("acc_status");
	    		String acc_password = rs.getString("acc_password");
	    		return new Account(account_id, username, account_type, customer_id, staff_id,acc_status,acc_password);
	    	}
	    	
	    	return null;
	    }

	    public void updateAccount(Account account) throws SQLException 
	    {
	    	 String sql = "UPDATE Account SET username = ?, account_type = ?, staff_id = ?, customer_id = ?, acc_status = ?, acc_password = ? WHERE account_id = ?";
	    	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	        stmt.setString(1, account.getUsername());
	    	        stmt.setString(2, account.getAccountType());
	    	        stmt.setString(3, account.getStaffId());;
	    	        stmt.setString(4, account.getCustomerId());
	    	        stmt.setBoolean(5, account.isAcc_status());
	    	        stmt.setString(6, account.getAcc_password());
	    	        stmt.setString(7, account.getAccountId());
	    	        stmt.executeUpdate();
	    	    } catch (SQLException e) {
	    	        throw e;
	    	    }
	    }

	    public void deleteAccount(Account account) throws SQLException {
	    	String sql = "UPDATE Account SET acc_status = 0 WHERE account_id=?";;
	    	PreparedStatement stmt = conn.prepareStatement(sql);
	    	stmt.setString(1, account.getAccountId());
	    	stmt.executeUpdate();
	        }
	    public boolean checkLogin(String username, String password) throws SQLException {
	        String sql = "SELECT * FROM Account WHERE username = ? AND acc_password = ? AND acc_status = 1";
	        try (PreparedStatement statement = conn.prepareStatement(sql)) {
	            statement.setString(1, username);
	            statement.setString(2, password);
	            ResultSet rs = statement.executeQuery();
	            return rs.next(); 
	    }
	    }
	        public void SignupCustomer(Account account,Customer customer) throws SQLException
	        {
	        	 String customerId = generateCustomerId();
	        	 String accountId = UUID.randomUUID().toString().substring(0, 10);
	        	 String customerSql = "INSERT INTO Customer (customer_id, full_name, email, phone_number, image_link, cus_address, gender, birth_day) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	             try (PreparedStatement stmt = conn.prepareStatement(customerSql)) {
	                  // Tạo customer_id mới
	                stmt.setString(1, customerId);
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
	                
	             } catch (SQLException e) {
	                 throw e;
	             }

	             // Thêm tài khoản vào bảng Account
	             String accountSql = "INSERT INTO Account (account_id, username, account_type, staff_id, customer_id, acc_status, acc_password) VALUES (?, ?, 'Customer', NULL, ?, ?, ?)";
	             try (PreparedStatement accountStmt = conn.prepareStatement(accountSql)) {
	                 accountStmt.setString(1, accountId);
	                 accountStmt.setString(2, account.getUsername());
	                 accountStmt.setString(3, customerId); // Sử dụng customer_id đã tạo
	                 accountStmt.setBoolean(4, account.isAcc_status());
	                 accountStmt.setString(5, account.getAcc_password());
	                 accountStmt.executeUpdate();
	             } catch (SQLException e) {
	                 throw e;
	             }
	        }
	        private String generateCustomerId() throws SQLException {
	            String customerId = null;
	            String sql = "SELECT customer_id FROM Customer WHERE customer_id = ?";
	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                ResultSet rs;
	                do {
	                	customerId = UUID.randomUUID().toString().substring(0, 10);
	                    stmt.setString(1, customerId);
	                    rs = stmt.executeQuery();
	                } while (rs.next()); // Loop until a unique customer_id is found
	            } 
	            catch (SQLException e) {
	                throw e;
	            }
	            return customerId;
	        }
	        private String generateStaffId() throws SQLException {
	            String staffId = null;
	            String sql = "SELECT staff_id FROM Staff WHERE staff_id = ?";
	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                ResultSet rs;
	                do {
	                	staffId = UUID.randomUUID().toString().substring(0, 10);
	                    stmt.setString(1, staffId);
	                    rs = stmt.executeQuery();
	                } while (rs.next()); // Loop until a unique customer_id is found
	            } 
	            catch (SQLException e) {
	                throw e;
	            }
	            return staffId;
	        }
	        public void SignupStaff(Account account, Staff staff) throws SQLException {
	            String staffId = generateStaffId();
	            String accountId = UUID.randomUUID().toString().substring(0, 10);
	            String staffSql = "INSERT INTO Staff (staff_id, full_name, email, phone_number, gender, birth_day, cic, staff_address, image_link) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	            try (PreparedStatement stmt = conn.prepareStatement(staffSql)) {
	                stmt.setString(1, staffId);
	                stmt.setString(2, staff.getFullName());
	                stmt.setString(3, staff.getEmail());
	                stmt.setString(4, staff.getPhoneNumber());
	                stmt.setBoolean(5, staff.isGender());

	                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	                Date date = null;
	         		try {
	         			java.util.Date utilDate = dateFormat.parse(staff.getBirthDay()); // Chuyển đổi chuỗi thành kiểu
	         																				// java.util.Date
	         			date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
	         		} catch (ParseException e) {
	         			e.printStackTrace();
	         		}

	                stmt.setDate(6, date);
	                stmt.setString(7, staff.getCic());
	                stmt.setString(8, staff.getAddress());
	                stmt.setString(9, staff.getImageLink());

	                stmt.executeUpdate();
	            } catch (SQLException e) {
	                throw e;
	            }

	            String accountSql = "INSERT INTO Account (account_id, username, account_type, staff_id, customer_id, acc_status, acc_password) VALUES (?, ?, 'Staff', ?, NULL, ?, ?)";

	            try (PreparedStatement accountStmt = conn.prepareStatement(accountSql)) {
	                accountStmt.setString(1, accountId);
	                accountStmt.setString(2, account.getUsername());
	                accountStmt.setString(3, staffId);
	                accountStmt.setBoolean(4, account.isAcc_status());
	                accountStmt.setString(5, account.getAcc_password());

	                accountStmt.executeUpdate();
	            } catch (SQLException e) {
	                throw e;
	            }
	    }
}
	    
	    
