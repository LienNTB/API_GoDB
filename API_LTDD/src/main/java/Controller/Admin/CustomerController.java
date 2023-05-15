package Controller.Admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;

import java.sql.SQLException;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.CustomerDAO;
import DBConnect.DBConnect;

import Model.Customer;


@WebServlet(urlPatterns = { "/admin/customer" })
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDAO customerDAO;
	private Connection conn;

	public void init() throws ServletException {
		try {
			DBConnect dbConnect = new DBConnect();
			this.conn = dbConnect.getConnection();
			customerDAO = new CustomerDAO(conn);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	public CustomerController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		List<Customer> customers = null;
		try {
			customers = customerDAO.getAllCustomers();
			ObjectMapper obj = new ObjectMapper();
			obj.writeValue(response.getOutputStream(), customers);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String customerId = request.getParameter("customer_id");

		if ("search".equals(action)) {
			try {
				Customer customer = customerDAO.getCustomerById(customerId);
				if (customer != null) {
					ObjectMapper obj = new ObjectMapper();
					obj.writeValue(response.getOutputStream(), customer);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} 
		///////////////////////
		else if ("add".equals(action)) {
			String fullName = request.getParameter("full_name");
			String email = request.getParameter("email");
			String phoneNumber = request.getParameter("phone_number");
			String imageLink = request.getParameter("image_link");
			String address = request.getParameter("address");
			boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
			
			String birthDayString = request.getParameter("birth_day");
			System.out.println(birthDayString);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
			
			
			Customer newCustomer = new Customer(customerId, fullName, email, phoneNumber, imageLink, address, gender,
					birthDay);
			try {
				customerDAO.addCustomer(newCustomer);
				response.getWriter().write("Thêm khách hàng thành công.");
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().write("Lỗi khi thêm khách hàng.");
				System.out.println("Lỗi add khách hàng rồi bạn ơi");
			}
		}
		////////////////////////////////
		else if ("update".equals(action)) {
			try {
				Customer customer = customerDAO.getCustomerById(customerId);
				if (customer == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
				String fullName = request.getParameter("full_name");
				String email = request.getParameter("email");
				String phoneNumber = request.getParameter("phone_number");
				String imageLink = request.getParameter("image_link");
				String address = request.getParameter("address");
				boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
				String birthDayString = request.getParameter("birth_day");
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
		            
				customer.setFullName(fullName);
				customer.setEmail(email);
				customer.setPhoneNumber(phoneNumber);
				customer.setImageLink(imageLink);
				customer.setAddress(address);
				customer.setGender(gender);
				customer.setBirthDay(birthDay);
				customerDAO.updateCustomer(customer);
				response.getWriter().write("Cập nhật khách hàng thành công.");
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().write("Lỗi khi cập nhật khách hàng.");
			}
		}
		///////////////////////////////
		else if ("delete".equals(action)) {
			try {
				Customer customer = customerDAO.getCustomerById(customerId);
				if (customer == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
				customerDAO.deleteCustomer(customer);
				response.getWriter().write("Xóa khách hàng thành công");
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().write("Lỗi khi xóa khách hàng.");
			}
		}
	}
}
