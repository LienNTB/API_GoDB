package Controller.Admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;


import DAO.TourGuiderDAO;
import DBConnect.DBConnect;
import Model.TourGuider;

@WebServlet(urlPatterns = "/admin/tourguiders")
public class TourGuiderController extends HttpServlet {
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private TourGuiderDAO tourGuiderDAO;
		
	


	public void init() throws ServletException {
		try {
			DBConnect dbConnect = new DBConnect();
			this.conn = dbConnect.getConnection();
			tourGuiderDAO = new TourGuiderDAO(conn);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * 
	 */
	public TourGuiderController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		List<TourGuider> tourguiders = null;
		try {
			tourguiders = tourGuiderDAO.getAllTourGuiders();
			ObjectMapper obj = new ObjectMapper();
			obj.writeValue(resp.getOutputStream(),tourguiders);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("application/json;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		String tourGuiderId = req.getParameter("tourguiderid");
		
		if("search".equals(action)) {
			try {
				TourGuider tourguider = tourGuiderDAO.getTourGuiderById(tourGuiderId);
				if (tourguider != null) {
					ObjectMapper obj = new ObjectMapper();
					obj.writeValue(resp.getOutputStream(), tourguider);
				} else {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				// TODO: handle exception
			}
		}
		else if("add".equals(action)){
			String name = req.getParameter("name");
			String birthDayString = req.getParameter("birthday"); 
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
			Boolean gender = Boolean.parseBoolean(req.getParameter("gender"));
			String adddress = req.getParameter("address");
			String tel = req.getParameter("tel");
			String email = req.getParameter("email");
			String imageLink = req.getParameter("image_link");
			String cic = req.getParameter("cic");
			//BigDecimal salary = BigDecimal.valueOf(req.getParameter("salary"));
			TourGuider newTourGuider = new TourGuider(tourGuiderId, name, birthDay, false, adddress, tel, email, imageLink, cic, null);
			try {
				tourGuiderDAO.addTourGuider(newTourGuider);
				resp.getWriter().write("Thêm hướng dẫn viên du lịch thành công");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resp.getWriter().write("Lỗi khi thêm .");
				System.out.println("Lỗi thêm hướng dẫn viên");
			}
			
		}
		else if("update".equals(action)) {
			try {
				TourGuider tourGuider = tourGuiderDAO.getTourGuiderById(tourGuiderId);
				if (tourGuider == null) {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
				String name = req.getParameter("name");
				String birthDayString = req.getParameter("birthday"); 
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
				Boolean gender = Boolean.parseBoolean(req.getParameter("gender"));
				String adddress = req.getParameter("address");
				String tel = req.getParameter("tel");
				String email = req.getParameter("email");
				String imageLink = req.getParameter("image_link");
				String cic = req.getParameter("cic");
				//BigDecimal salary = BigDecimal.valueOf(req.getParameter("salary"));
				
				tourGuider.setFullName(name);
				tourGuider.setBirthDay(birthDay);
				tourGuider.setGender(false);
				tourGuider.setAddress(adddress);
				tourGuider.setTel(tel);
				tourGuider.setEmail(email);
				tourGuider.setImageLink(imageLink);
				tourGuider.setCic(cic);
				tourGuider.setSalary(null);
				tourGuiderDAO.updateTourGuider(tourGuider);
				resp.getWriter().write("Cập nhật thành công");
				
				
			}
			catch (SQLException e) {
				e.printStackTrace();
				resp.getWriter().write("Cập nhật thất bại");
				// TODO: handle exception
			}
		}
	}
}
