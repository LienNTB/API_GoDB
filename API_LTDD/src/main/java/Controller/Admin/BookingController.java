package Controller.Admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.AccountDAO;
import DAO.BookingDAO;
import DBConnect.DBConnect;
import Model.Booking;
import Model.Customer;


/**
 * Servlet implementation class BookingController
 */
@WebServlet("/admin/booking")
public class BookingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookingDAO bookingDAO ;
	private Connection conn;

	public void init() throws ServletException {
		try {
			DBConnect dbConnect = new DBConnect();
			this.conn = dbConnect.getConnection();
			bookingDAO = new BookingDAO(conn);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		List<Booking> bookings = null;
		try {
			bookings = bookingDAO.getAllBookings();
			ObjectMapper obj = new ObjectMapper();
			obj.writeValue(response.getOutputStream(), bookings);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
	
		if ("search".equals(action)) {
			String bookingType = request.getParameter("booking_type");
			try {
				List<Booking> bookings = bookingDAO.getBookingsByType(bookingType);
				if (bookings != null) {
					ObjectMapper obj = new ObjectMapper();
					obj.writeValue(response.getOutputStream(), bookings);
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
			String bookingId = request.getParameter("booking_id");
			String bookingType = request.getParameter("booking_type");
			String customerId = request.getParameter("customer_id");
			String staffId = request.getParameter("staff_id");
			String vehicleId = request.getParameter("vehicle_id");
			String tourId = request.getParameter("tour_id");
			String hotelId = request.getParameter("hotel_id");	
			String bookingDayString = request.getParameter("booking_date");
			System.out.println(bookingDayString);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date bookingDay = null;
			if (bookingDayString != null && !bookingDayString.isEmpty()) {
	            try {
	                java.util.Date utilDate = dateFormat.parse(bookingDayString); // Chuyển đổi chuỗi thành kiểu java.util.Date
	                bookingDay = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
	                System.out.println(utilDate);
	            } catch (ParseException e) {
	                e.printStackTrace();
					System.out.println("Lỗi định dạng date booking rồi bạn ơi");
	            }
	        }
			Booking newBooking = new Booking(bookingId, customerId, staffId, vehicleId, tourId, hotelId, bookingDay,
					bookingType);

			try {
				bookingDAO.addBooking(newBooking);
				response.getWriter().write("Thêm giao dịch thành công.");
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().write("Lỗi khi thêm giao dịch.");
				System.out.println("Lỗi add giao dịch rồi bạn ơi");
			}
		}
			else if ("addTour".equals(action)) {
//				String bookingId = request.getParameter("booking_id");
				String customerId = request.getParameter("customer_id");
				String tourId = request.getParameter("tour_id");	
				String bookingDayString = request.getParameter("booking_date");
				System.out.println(bookingDayString);
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date bookingDay = null;
				if (bookingDayString != null && !bookingDayString.isEmpty()) {
		            try {
		                java.util.Date utilDate = dateFormat.parse(bookingDayString); // Chuyển đổi chuỗi thành kiểu java.util.Date
		                bookingDay = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
		                System.out.println(utilDate);
		            } catch (ParseException e) {
		                e.printStackTrace();
						System.out.println("Lỗi định dạng date booking rồi bạn ơi");
		            }
		        }
				Booking newBooking = new Booking(null, customerId,null, null, tourId, null, bookingDay, null);

				try {
					bookingDAO.addBookingTour(newBooking);
					 JSONObject jsonResponse = new JSONObject();
			            jsonResponse.put("success", "true");
			            jsonResponse.put("message", "Đặt tour thành công");
			            response.setContentType("application/json");
			            response.setCharacterEncoding("UTF-8");
			            response.getWriter().write(jsonResponse.toString());
				} catch (SQLException e) {
					e.printStackTrace();
					response.getWriter().write("Lỗi khi đặt tour.");
//					System.out.println("Lỗi add giao dịch rồi bạn ơi");
				}
//			
//			
//			Customer newCustomer = new Customer(customerId, fullName, email, phoneNumber, imageLink, address, gender,
//					birthDay);
//			try {
//				customerDAO.addCustomer(newCustomer);
//				response.getWriter().write("Thêm khách hàng thành công.");
//			} catch (SQLException e) {
//				e.printStackTrace();
//				response.getWriter().write("Lỗi khi thêm khách hàng.");
//				System.out.println("Lỗi add khách hàng rồi bạn ơi");
//			}
//		}
//		////////////////////////////////
//		else if ("update".equals(action)) {
//			try {
//				Customer customer = customerDAO.getCustomerById(customerId);
//				if (customer == null) {
//					response.sendError(HttpServletResponse.SC_NOT_FOUND);
//					return;
//				}
//				String fullName = request.getParameter("full_name");
//				String email = request.getParameter("email");
//				String phoneNumber = request.getParameter("phone_number");
//				String imageLink = request.getParameter("image_link");
//				String address = request.getParameter("address");
//				boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
//				String birthDayString = request.getParameter("birth_day");
//				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//				Date birthDay = null;
//				if (birthDayString != null && !birthDayString.isEmpty()) {
//		            try {
//		                java.util.Date utilDate = dateFormat.parse(birthDayString); // Chuyển đổi chuỗi thành kiểu java.util.Date
//		                birthDay = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
//		                System.out.println(utilDate);
//		            } catch (ParseException e) {
//		                e.printStackTrace();
//						System.out.println("Lỗi định dạng date khách hàng rồi bạn ơi");
//		            }
//		        }
//		            
//				customer.setFullName(fullName);
//				customer.setEmail(email);
//				customer.setPhoneNumber(phoneNumber);
//				customer.setImageLink(imageLink);
//				customer.setAddress(address);
//				customer.setGender(gender);
//				customer.setBirthDay(birthDay);
//				customerDAO.updateCustomer(customer);
//				response.getWriter().write("Cập nhật khách hàng thành công.");
//			} catch (SQLException e) {
//				e.printStackTrace();
//				response.getWriter().write("Lỗi khi cập nhật khách hàng.");
//			}
//		}
	}

	}}
