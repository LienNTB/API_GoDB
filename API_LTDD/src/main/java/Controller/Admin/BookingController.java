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
import Model.Account;
import Model.Booking;
import Model.Customer;

/**
 * Servlet implementation class BookingController
 */
@WebServlet("/admin/booking")
public class BookingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookingDAO bookingDAO;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		if ("search".equals(action)) {
			String customerId = request.getParameter("customer_id");
			try {
				List<Booking> bookings = bookingDAO.getBookingsByCustomerId(customerId);
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
					java.util.Date utilDate = dateFormat.parse(bookingDayString); // Chuyển đổi chuỗi thành kiểu
																					// java.util.Date
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
		} else if ("addTour".equals(action)) {
			String customerId = request.getParameter("customer_id");
			String tourId = request.getParameter("tour_id");
			String bookingDayString = request.getParameter("booking_date");
			System.out.println(bookingDayString);

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date bookingDay = null;
			if (bookingDayString != null && !bookingDayString.isEmpty()) {
				try {
					java.util.Date utilDate = dateFormat.parse(bookingDayString); // Chuyển đổi chuỗi thành kiểu
																					// java.util.Date
					bookingDay = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
					System.out.println(utilDate);
				} catch (ParseException e) {
					e.printStackTrace();
					System.out.println("Lỗi định dạng date booking rồi bạn ơi");
				}
			}
			Booking newBooking = new Booking(null, customerId, null, null, tourId, null, bookingDay, null);

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
			}
		} else if ("addhotel".equals(action)) {
			String customerId = request.getParameter("customer_id");
			String hotelId = request.getParameter("hotel_id");
			String bookingDayString = request.getParameter("booking_date");
			System.out.println(bookingDayString);

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date bookingDay = null;
			if (bookingDayString != null && !bookingDayString.isEmpty()) {
				try {
					java.util.Date utilDate = dateFormat.parse(bookingDayString); // Chuyển đổi chuỗi thành kiểu
																					// java.util.Date
					bookingDay = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date

				} catch (ParseException e) {
					e.printStackTrace();
					System.out.println("Lỗi định dạng date booking rồi bạn ơi");
				}
			}
			Booking newBooking = new Booking(null, customerId, null, null, null, hotelId, bookingDay, null);

			try {
				bookingDAO.addBookingHotel(newBooking);
				JSONObject jsonResponse = new JSONObject();
				jsonResponse.put("success", "true");
				jsonResponse.put("message", "Đặt hotel thành công");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonResponse.toString());
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().write("Lỗi khi đặt hotel.");
			}
		} else if ("addVehicle".equals(action)) {
			String customerId = request.getParameter("customer_id");
			String VehicleId = request.getParameter("vehicle_id");
			String bookingDayString = request.getParameter("booking_date");
			System.out.println(bookingDayString);

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date bookingDay = null;
			if (bookingDayString != null && !bookingDayString.isEmpty()) {
				try {
					java.util.Date utilDate = dateFormat.parse(bookingDayString); // Chuyển đổi chuỗi thành kiểu
																					// java.util.Date
					bookingDay = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date

				} catch (ParseException e) {
					e.printStackTrace();
					System.out.println("Lỗi định dạng date booking rồi bạn ơi");
				}
			}
			Booking newBooking = new Booking(null, customerId, null, VehicleId, null, null, bookingDay, null);

			try {
				bookingDAO.addBookinVehicle(newBooking);
				JSONObject jsonResponse = new JSONObject();
				jsonResponse.put("success", "true");
				jsonResponse.put("message", "Đặt vehicle thành công");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonResponse.toString());
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().write("Lỗi khi đặt vehicle.");
			}

		}
		////////////////////////////////
		else if ("getforStaff".equals(action)) {
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");

			List<Booking> bookings = null;
			try {
				bookings = bookingDAO.getBookingForStaff();
				ObjectMapper obj = new ObjectMapper();
				obj.writeValue(response.getOutputStream(), bookings);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("getforStaffUsed".equals(action)) {
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");

			List<Booking> bookings = null;
			try {
				bookings = bookingDAO.getBookingForStaffUsed();
				ObjectMapper obj = new ObjectMapper();
				obj.writeValue(response.getOutputStream(), bookings);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("searchId".equals(action)) {
			try {
				String bookingId = request.getParameter("booking_id");
				Booking booking = bookingDAO.getBookingById(bookingId);
				if (booking != null) {
					ObjectMapper obj = new ObjectMapper();
					obj.writeValue(response.getOutputStream(), booking);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else if ("updateBookingforStaff".equals(action)) {
			String bookingId = request.getParameter("booking_id");
			try {
				Booking booking = bookingDAO.getBookingById(bookingId);
				if (booking == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
				String staffId = request.getParameter("staff_id");
				booking.setStaffId(staffId);
				bookingDAO.updateStatusBooking(booking);
				JSONObject jsonResponse = new JSONObject();
				jsonResponse.put("success", "true");
				jsonResponse.put("message", "Cập nhật trạng thái đơn hàng thành công");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonResponse.toString());
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().write("Lỗi khi cập nhật tình trạng xác nhận.");
			}

		} else if ("deleteBooking".equals(action)) {
			String bookingId = request.getParameter("booking_id");
			try {
				Booking booking = bookingDAO.getBookingById(bookingId);
				if (booking == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
				bookingDAO.deleteBooking(booking);
				JSONObject jsonResponse = new JSONObject();
				jsonResponse.put("success", "true");
				jsonResponse.put("message", "Xóa đơn hàng thành công");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonResponse.toString());
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().write("Lỗi khi xóa đơn hàng");
			}
		} else if ("arrange".equals(action)) {
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");

			List<Booking> bookings = null;
			try {
				bookings = bookingDAO.ArrangeBookings();
				ObjectMapper obj = new ObjectMapper();
				obj.writeValue(response.getOutputStream(), bookings);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("arrangedesc".equals(action)) {
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");

			List<Booking> bookings = null;
			try {
				bookings = bookingDAO.ArrangeBookingsDESC();
				ObjectMapper obj = new ObjectMapper();
				obj.writeValue(response.getOutputStream(), bookings);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
