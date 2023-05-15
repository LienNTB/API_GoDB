package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import DBConnect.DBConnect;
import Model.Account;

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
	    }
	    
