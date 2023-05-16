package Controller.Admin;

import java.io.IOException;
import org.json.JSONObject;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;

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
import Model.Customer;
import Model.Staff;

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
		response.setContentType("application/json");
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
		 else if("login".equals(action))
		 {
			 	String username = request.getParameter("username");
			    String password = request.getParameter("password");
			    try {
			    	String accountType = accountDAO.checkLogin(username, password);
			        if (accountType != null) {
			        	JSONObject jsonResponse = new JSONObject();
			            jsonResponse.put("success", "true");
			            jsonResponse.put("message", "Đăng nhập thành công.");
			            jsonResponse.put("account_type", accountType);
			            response.setContentType("application/json");
			            response.getWriter().write(jsonResponse.toString());
			        } else {
			        	 JSONObject jsonResponse = new JSONObject();
			             jsonResponse.put("success", "false");
			             jsonResponse.put("message", "Sai tên đăng nhập hoặc mật khẩu.");
			             response.setContentType("application/json");
			             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			             response.getWriter().write(jsonResponse.toString());
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			    }
		 }
		 else if("signupcustomer".equals(action))
		 {
			 	String username = request.getParameter("username");
			    String fullName = request.getParameter("full_name");
			    String email = request.getParameter("email");
			    String phoneNumber = request.getParameter("phone_number");
			    String imageLink = request.getParameter("image_link");
			    String address = request.getParameter("address");
			    boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
			    String birthDayString = request.getParameter("birth_day");
				System.out.println(birthDayString);
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date birthDay = null;
				if (birthDayString != null && !birthDayString.isEmpty()) {
		            try {
		                java.util.Date utilDate = dateFormat.parse(birthDayString); // Chuyển đổi chuỗi thành kiểu java.util.Date
		                birthDay = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
		                System.out.println(utilDate);
		            } catch (ParseException e) {
		                e.printStackTrace();
						System.out.println("Lỗi định dạng date khách hàng rồi bạn ơi");
		            }
		        }
			    String accPassword = request.getParameter("acc_password");

			    Account account = new Account(null, username, null, null, null, true, accPassword);
			    Customer customer =  new Customer(null, fullName, email, phoneNumber, imageLink, address, gender,
						birthDay);

			    try {
			        accountDAO.SignupCustomer(account, customer);
			        JSONObject jsonResponse = new JSONObject();
		            jsonResponse.put("success", "true");
		            jsonResponse.put("message", "Đăng kí tài khoản thành công.");
		            jsonResponse.put("account_type", "Customer");
		            response.setContentType("application/json");
		            response.getWriter().write(jsonResponse.toString());
			    } catch (SQLException e) {
			        e.printStackTrace();
			        response.getWriter().write("Lỗi khi đăng ký tài khoản.");
			    }
		 }
		 else if("signupstaff".equals(action))
		 {
			 	String username = request.getParameter("username");
			    String fullName = request.getParameter("full_name");
			    String email = request.getParameter("email");
			    String phoneNumber = request.getParameter("phone_number");
			    String imageLink = request.getParameter("image_link");
			    boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
			    String birthDayString = request.getParameter("birth_day");
			    String cic = request.getParameter("cic");
			    String staffAddress = request.getParameter("staff_address");

			    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			    Date birthDay = null;
			    if (birthDayString != null && !birthDayString.isEmpty()) {
			        try {
			            java.util.Date utilDate = dateFormat.parse(birthDayString);
			            birthDay = new Date(utilDate.getTime());
			        } catch (ParseException e) {
			            e.printStackTrace();
			            System.out.println("Lỗi định dạng ngày sinh của nhân viên.");
			        }
			    }

			    String accPassword = request.getParameter("acc_password");
			    Account account = new Account(null, username, null, null, null, true, accPassword);
			    Staff staff = new Staff(null, fullName, email, phoneNumber, gender, birthDay, cic, staffAddress, imageLink);

			    try {
			        accountDAO.SignupStaff(account, staff);
			        response.getWriter().write("Đăng ký tài khoản nhân viên thành công.");
			    } catch (SQLException e) {
			        e.printStackTrace();
			        response.getWriter().write("Lỗi khi đăng ký tài khoản nhân viên.");
			    }
		 }
		 }
	


	public void destroy() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}