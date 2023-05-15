package Controller.Admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.RoomDAO;
import DBConnect.DBConnect;
import Model.Place;
import Model.Room;

@WebServlet("/admin/rooms" )
public class AdminRoomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoomDAO roomDAO;
	private Connection conn;

	public void init() throws ServletException {
		try {
			DBConnect dbConnect = new DBConnect();
			this.conn = dbConnect.getConnection();
			roomDAO = new RoomDAO(conn);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
   
    public AdminRoomController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		List<Room> rooms = null;
		
		try {
			rooms = roomDAO.getRooms();
			ObjectMapper obj = new ObjectMapper();
			obj.writeValue(response.getOutputStream(), rooms);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		int roomId = Integer.parseInt(request.getParameter("room_id"));

		// lấy thông tin một place
		if ("search".equals(action)) {
			try {
				Room room = roomDAO.getRoomById(roomId);
				if (room != null) {
					ObjectMapper obj = new ObjectMapper();
					obj.writeValue(response.getOutputStream(), room);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} 
		else if ("add".equals(action)) {

			String hoteId = request.getParameter("hotel_id");
			String roomNumber = request.getParameter("room_number");
			String roomType = request.getParameter("room_type");
			BigDecimal rentalRate = new BigDecimal(request.getParameter("rental_rate"));
			int roomCapacity = Integer.parseInt(request.getParameter("room_capacity"));
			String roomStatus = request.getParameter("room_status");
			
			Room newRoom = new Room(roomId, hoteId, roomNumber, roomType, 
					rentalRate, roomCapacity, roomStatus);

			try {
				roomDAO.addRoom(newRoom);
				response.setStatus(HttpServletResponse.SC_CREATED);
				response.getWriter().write("Thêm phòng thành công." +"\n");
				response.getWriter().write(new ObjectMapper().writeValueAsString(newRoom));
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().write("Lỗi khi thêm phòng.");
			}
		}
		else if("update".equals(action))
		{
			try {
				Room room = roomDAO.getRoomById(roomId);

				if (room == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
				
				String hoteId = request.getParameter("hotel_id");
				String roomNumber = request.getParameter("room_number");
				String roomType = request.getParameter("room_type");
				BigDecimal rentalRate = new BigDecimal(request.getParameter("rental_rate"));
				int roomCapacity = Integer.parseInt(request.getParameter("room_capacity"));
				String roomStatus = request.getParameter("room_status");

				room.setHotelId(hoteId);
				room.setRoomNumber(roomNumber);
				room.setRoomType(roomType);
				room.setRentalRate(rentalRate);
				room.setRoomCapacity(roomCapacity);
				room.setRoomStatus(roomStatus);
				roomDAO.updateRoom(room);
				
				Room roomcheck = roomDAO.getRoomById(roomId);
				response.getWriter().write("Cập nhật phòng thành công."+ "\n");
				response.getWriter().write(new ObjectMapper().writeValueAsString(roomcheck));
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().write("Lỗi khi sửa phòng.");
			}
		}
	}


}
