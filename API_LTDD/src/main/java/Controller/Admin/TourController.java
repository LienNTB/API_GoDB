package Controller.Admin;

import java.io.IOException;
import java.math.BigDecimal;
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

import com.fasterxml.jackson.databind.ObjectMapper;


import DAO.TourDAO;
import DBConnect.DBConnect;
import Model.Tour;

@WebServlet(urlPatterns = "/admin/tours")
public class TourController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TourDAO tourDAO;
	private Connection conn;
	public void init() throws ServletException {
		try {
			DBConnect dbConnect = new DBConnect();
			this.conn = dbConnect.getConnection();
			tourDAO = new TourDAO(conn);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * 
	 */
	public TourController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		// String path = req.getContextPath()+req.getServletPath();

		List<Tour> tours = null;

		try {
			tours = tourDAO.getAllTours();
			ObjectMapper obj = new ObjectMapper();
			obj.writeValue(resp.getOutputStream(), tours);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		{
			resp.setContentType("application/json;charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");
			String action = req.getParameter("action");
			String tourId = req.getParameter("tour_id");
		if("search".equals(action)) {
			try {
				Tour tour = tourDAO.getTourById(tourId);
				if(tour != null) {
					ObjectMapper obj = new ObjectMapper();
					obj.writeValue(resp.getOutputStream(), tour);
					
				}else {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
					
				}
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
		else if ("add".equals(action)) {
			String tourid = req.getParameter("tour_id");
			String tourGuiderId = req.getParameter("tour_guider_id");
			String placeId = req.getParameter("place_id");
			String tourName = req.getParameter("tour_name");
			String placeGo = req.getParameter("pace_go");
			String dateGoString = req.getParameter("date_go");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			if (dateGoString != null && !dateGoString.isEmpty()) {
				try {
					java.util.Date utilDate = dateFormat.parse(dateGoString); // Chuyển đổi chuỗi thành kiểu
																				// java.util.Date
					date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
					System.out.println(utilDate);
				} catch (ParseException e) {
					e.printStackTrace();
					System.out.println("Lỗi định dạng");
				}
			}
			
			String dateBackString = req.getParameter("date_back");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = null;
			if (dateBackString != null && !dateBackString.isEmpty()) {
				try {
					java.util.Date utilDate = dateFormat1.parse(dateBackString); // Chuyển đổi chuỗi thành kiểu
																				// java.util.Date
					date1 = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
					System.out.println(utilDate);
				} catch (ParseException e) {
					e.printStackTrace();
					System.out.println("Lỗi định dạng");
				}
			}
			int numPerson = Integer.parseInt(req.getParameter("num_person"));
			//BigDecimal price = Bi
			String imageLink = req.getParameter("image_link");
			Boolean state = Boolean.parseBoolean(req.getParameter("state"));
			
			Tour newTour = new Tour(tourid, tourGuiderId, placeId, tourName, placeGo, date, date1, numPerson, null, imageLink, false);
			try {
				tourDAO.addTour(newTour);
				resp.getWriter().write("Thêm tour thành công");
			} catch (SQLException e) {
				e.printStackTrace();
				resp.getWriter().write("Lỗi khi thêm tour");
				System.out.println("Lỗi thêm tour");
				// TODO: handle exception
			}
		
		}
		else if("update".equals(action)) {
			try {
				Tour tour = tourDAO.getTourById(tourId);
				if(tour == null) {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
					
				}
				String tourid = req.getParameter("tour_id");
				String tourGuiderId = req.getParameter("tour_guider_id");
				String placeId = req.getParameter("place_id");
				String tourName = req.getParameter("tour_name");
				String placeGo = req.getParameter("pace_go");
				String dateGoString = req.getParameter("date_go");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				if (dateGoString != null && !dateGoString.isEmpty()) {
					try {
						java.util.Date utilDate = dateFormat.parse(dateGoString); // Chuyển đổi chuỗi thành kiểu
																					// java.util.Date
						date = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
						System.out.println(utilDate);
					} catch (ParseException e) {
						e.printStackTrace();
						System.out.println("Lỗi định dạng");
					}
				}
				
				String dateBackString = req.getParameter("date_back");
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = null;
				if (dateBackString != null && !dateBackString.isEmpty()) {
					try {
						java.util.Date utilDate = dateFormat1.parse(dateBackString); // Chuyển đổi chuỗi thành kiểu
																					// java.util.Date
						date1 = new Date(utilDate.getTime()); // Chuyển đổi java.util.Date thành java.sql.Date
						System.out.println(utilDate);
					} catch (ParseException e) {
						e.printStackTrace();
						System.out.println("Lỗi định dạng");
					}
				}
				int numPerson = Integer.parseInt(req.getParameter("num_person"));
				//BigDecimal price = Bi
				String imageLink = req.getParameter("image_link");
				Boolean state = Boolean.parseBoolean(req.getParameter("state"));
				
				tour.setTourName(tourName);
				tour.setPlaceGo(placeGo);
				tour.setDateGo(date);
				tour.setDateBack(date1);
				tour.setNumPerson(numPerson);
				tour.setPrice(null);
				tour.setImageLink(imageLink);
				tour.setState(false);
				tourDAO.updateTour(tour);
				resp.getWriter().write("Cập nhật thành công");
				
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				resp.getWriter().write("Lỗi khi cập nhật tuor");
				// TODO: handle exception
			}
		}
		}
	}


}
