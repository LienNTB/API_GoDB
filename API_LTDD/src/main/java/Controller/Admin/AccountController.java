package Controller.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import DAO.AccountDAO;
import DBConnect.DBConnect;
import Model.Account;

@WebServlet(urlPatterns = { "/admin/account" })
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountDAO accountDAO ;
	private Connection conn;

	public void init() throws ServletException {
		try {
			DBConnect dbConnect = new DBConnect();
			this.conn = dbConnect.getConnection();
			accountDAO = new AccountDAO(conn);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}


	public AccountController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		List<Account> accounts = null;
		try {
			accounts = accountDAO.getAllAccounts();
			ObjectMapper obj = new ObjectMapper();
			obj.writeValue(response.getOutputStream(), accounts);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String accountId = request.getParameter("id");
		// Lấy thông tin một tài khoản
		if ("search".equals(action)) {
		    try {
		        Account account = accountDAO.getAccountById(accountId);
		        if (account != null) {
		            ObjectMapper obj = new ObjectMapper();
		            obj.writeValue(response.getOutputStream(), account);
		        } else {
		            response.sendError(HttpServletResponse.SC_NOT_FOUND);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    }
		}
		 else if ("add".equals(action)) 
			{
			 
				String username = request.getParameter("username");
				String accountType = request.getParameter("account_type");
				String customerId = request.getParameter("customer_id");
				String staffId = request.getParameter("staff_id");
				boolean accStatus = Boolean.parseBoolean(request.getParameter("acc_status"));
				String accPassword = request.getParameter("acc_password");
				Account newAccount = new Account(accountId, username, accountType, staffId, customerId, accStatus, accPassword);
				    try { 
				    	accountDAO.addAccount(newAccount);
				    	response.getWriter().write("Thêm tài khoản thành công.");
				    } catch (SQLException e) {
				        e.printStackTrace();
				        response.getWriter().write("Lỗi khi thêm tài khoản.");
				    }
			}
		 else if("update".equals(action))
		 {
			 try {
			 Account account = accountDAO.getAccountById(accountId);
			
			 	if (account == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
				String username = request.getParameter("username");
				String accountType = request.getParameter("account_type");
				String customerId = request.getParameter("customer_id");
				String staffId = request.getParameter("staff_id");
				boolean accStatus = Boolean.parseBoolean(request.getParameter("acc_status"));
				String accPassword = request.getParameter("acc_password");
				account.setUsername(username);
				account.setAccountType(accountType);
				account.setCustomerId(customerId);
				account.setStaffId(staffId);
				account.checkId();
				account.setAcc_password(accPassword);
				account.setAcc_status(accStatus);
				account.setAccountId(accountId);
				
				accountDAO.updateAccount(account);
				response.getWriter().write("Cập nhật tài khoản thành công.");
			} catch (SQLException e) {
				e.printStackTrace();
				 response.getWriter().write("Lỗi khi sửa tài khoản.");
			}
		 }
		 else if("delete".equals(action))
		 {
			 try {
				 	Account account = accountDAO.getAccountById(accountId);
					if (account == null) {
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
						return;
					}
					accountDAO.deleteAccount(account);
					response.getWriter().write("Xóa tài khoản thành công");
				} catch (SQLException e) {
					e.printStackTrace();
					response.getWriter().write("Lỗi khi xóa tài khoản");
				}
			}

		 }


//	protected void doPut(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String accountId = request.getParameter("id");
//		
//		try {
//			Account account = accountDAO.getAccountById(accountId);	
//			if (account == null) {
//				response.sendError(HttpServletResponse.SC_NOT_FOUND);
//				return;
//			}
//			String username = request.getParameter("username");
//			String accountType = request.getParameter("type");
//			String customerId = request.getParameter("customer_id");
//			String staffId = request.getParameter("staff_id");
//			Boolean status = Boolean.parseBoolean(request.getParameter("status"));
//			String password =request.getParameter("password");
//			account.setAccountId(accountId);
//			account.setUsername(username);
//			account.setAccountType(accountType);
//			account.setCustomerId(customerId);
//			account.setStaffId(staffId);
//			account.setAcc_password(password);
//			account.setAcc_status(status);
//			accountDAO.updateAccount(account);
//			response.setStatus(HttpServletResponse.SC_OK);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//		}
//	}

//	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		try {
//			String accountId = request.getParameter("account_id");
//
//			Account account = accountDAO.getAccountById(accountId);
//			if (account == null) {
//				response.sendError(HttpServletResponse.SC_NOT_FOUND);
//				return;
//			}
//
//			accountDAO.deleteAccount(account);
//			response.setStatus(HttpServletResponse.SC_OK);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//		}
//	}

	public void destroy() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}