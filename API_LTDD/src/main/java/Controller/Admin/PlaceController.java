package Controller.Admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import DAO.AccountDAO;
import DAO.PlaceDAO;
import Model.Account;
import Model.Place;
import DBConnect.DBConnect;

@WebServlet(urlPatterns = { "/admin/places" })
public class PlaceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PlaceDAO placeDAO;
	private Connection conn;

	public void init() throws ServletException {
		try {
			DBConnect dbConnect = new DBConnect();
			this.conn = dbConnect.getConnection();
			placeDAO = new PlaceDAO(conn);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	public PlaceController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		List<Place> places = null;
		// lấy list các place
		try {
			places = placeDAO.getPlaces();
			ObjectMapper obj = new ObjectMapper();
			obj.writeValue(response.getOutputStream(), places);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String placeId = request.getParameter("place_id");

		// lấy thông tin một place
		if ("search".equals(action)) {
			try {
				Place place = placeDAO.getPlaceById(placeId);
				if (place != null) {
					ObjectMapper obj = new ObjectMapper();
					obj.writeValue(response.getOutputStream(), place);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else if ("add".equals(action)) {

			String placeName = request.getParameter("place_id");
			String dec = request.getParameter("dec");
			String contents = request.getParameter("contents");
			String imageLink = request.getParameter("image_link");
			Place newPlace = new Place(placeId, placeName, dec, contents, imageLink);

			try {
				placeDAO.addPlace(newPlace);
				response.setStatus(HttpServletResponse.SC_CREATED);
				response.getWriter().write("Thêm địa điểm thành công." +"\n");
				response.getWriter().write(new ObjectMapper().writeValueAsString(newPlace));
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().write("Lỗi khi thêm địa điểm.");
			}
		}
		else if("update".equals(action))
		{
			try {
				Place place = placeDAO.getPlaceById(placeId);

				if (place == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
				String placeName = request.getParameter("place_name");
				String dec = request.getParameter("dec");
				String contents = request.getParameter("contents");
				String imageLink = request.getParameter("image_link");

				place.setPlaceName(placeName);
				place.setDec(dec);
				place.setContents(contents);
				place.setImageLink(imageLink);
				placeDAO.updatePlace(place);
				
				Place placecheck = placeDAO.getPlaceById(placeId);
				response.getWriter().write("Cập nhật địa điểm thành công."+ "\n");
				response.getWriter().write(new ObjectMapper().writeValueAsString(placecheck));
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().write("Lỗi khi sửa địa điểm.");
			}
		}

	}


}
