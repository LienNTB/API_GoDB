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
import DAO.StaffDAO;
import DBConnect.DBConnect;
import Model.Customer;
import Model.Staff;

@WebServlet("/admin/staffs")
public class AdminStaffController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StaffDAO staffDAO;
	private Connection conn;

	public void init() throws ServletException {
		try {
			DBConnect dbConnect = new DBConnect();
			this.conn = dbConnect.getConnection();
			staffDAO = new StaffDAO(conn);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
    public AdminStaffController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		List<Staff> staffs = null;
		try {
			staffs = staffDAO.getAllStaffs();
			ObjectMapper obj = new ObjectMapper();
			obj.writeValue(response.getOutputStream(), staffs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		{
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			String action = request.getParameter("action");
			String staffId = request.getParameter("staff_id");

			if ("search".equals(action)) {
				try {
					Staff staff = staffDAO.getStaffById(staffId);
					if (staff != null) {
						ObjectMapper obj = new ObjectMapper();
						obj.writeValue(response.getOutputStream(), staff);
					} else {
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
			}
			///////////////////
			else if ("add".equals(action)) {
				String fullName = request.getParameter("fullName");
				String email = request.getParameter("email");
				String phoneNumber = request.getParameter("phoneNumber");
				boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
				String cic = request.getParameter("cic");
				String address = request.getParameter("address");
				String imageLink = request.getParameter("imageLink");

				
				String birthDayString = request.getParameter("birthDay");

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date birthDay = null;
				if (birthDayString != null && !birthDayString.isEmpty()) {
					try {
						java.util.Date utilDate = dateFormat.parse(birthDayString); // Chuyển đổi chuỗi thành kiểu
																					// java.util.Date
						birthDay = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
						System.out.println(utilDate);
					} catch (ParseException e) {
						e.printStackTrace();
						System.out.println("Lỗi định dạng date nhân viên rồi bạn ơi");
					}
				}
				
				Staff newStaff = new Staff(staffId, fullName, email, phoneNumber, gender, birthDay, cic, address,
						imageLink);
				try {
					staffDAO.addStaff(newStaff);
					response.getWriter().write("Thêm nhân viên thành công.");
				} catch (SQLException e) {
					e.printStackTrace();
					response.getWriter().write("Lỗi khi thêm nhân viên.");
					System.out.println("Lỗi add nhân viên rồi bạn ơi");
				}
			} else if ("update".equals(action)) {

				try {
					Staff staff = staffDAO.getStaffById(staffId);
					if (staff == null) {
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
						return;
					}
					String fullName = request.getParameter("fullName");
					String email = request.getParameter("email");
					String phoneNumber = request.getParameter("phoneNumber");
					boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
					String birthDayString = request.getParameter("birthDay");
					String cic = request.getParameter("cic");
					String address = request.getParameter("address");
					String imageLink = request.getParameter("imageLink");

					System.out.println(birthDayString);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date birthDay = null;
					if (birthDayString != null && !birthDayString.isEmpty()) {
						try {
							java.util.Date utilDate = dateFormat.parse(birthDayString); // Chuyển đổi chuỗi thành kiểu
																						// java.util.Date
							birthDay = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
							System.out.println(utilDate);
						} catch (ParseException e) {
							e.printStackTrace();
							System.out.println("Lỗi định dạng date ");
						}
					}

					staff.setFullName(fullName);
					staff.setEmail(email);
					staff.setPhoneNumber(phoneNumber);
					staff.setGender(gender);
					staff.setBirthDay(birthDay);
					staff.setCic(cic);
					staff.setAddress(address);
					staff.setImageLink(imageLink);
					staffDAO.updateStaff(staff);
					response.getWriter().write("Cập nhật thành công");

				} catch (SQLException e) {
					e.printStackTrace();
					response.getWriter().write("Lỗi khi cập nhật khách hàng.");
					// TODO: handle exception
				}

			}

		}

	}

}
