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


import DAO.HotelDAO;
import DBConnect.DBConnect;
import Model.Account;
import Model.Customer;
import Model.Hotel;

@WebServlet(urlPatterns = { "/admin/hotel" })
public class HotelController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HotelDAO hotelDAO ;
	private Connection conn;

	public void init() throws ServletException {
		try {
			DBConnect dbConnect = new DBConnect();
			this.conn = dbConnect.getConnection();
			hotelDAO = new HotelDAO(conn);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotelController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		List<Hotel> hotels = null;
		try {
			hotels = hotelDAO.getAllHotels();
			ObjectMapper obj = new ObjectMapper();
			obj.writeValue(response.getOutputStream(), hotels);
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
	        String hotelId = request.getParameter("hotel_id");
	        if ("search".equals(action)) {
	            try {
	                Hotel hotel = hotelDAO.getHotelById(hotelId);
	                if (hotel != null) {
	                    ObjectMapper obj = new ObjectMapper();
	                    obj.writeValue(response.getOutputStream(), hotel);
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
			 
				String hotelName = request.getParameter("hotel_name");
				String hotelAddress = request.getParameter("hotel_address");
				String province = request.getParameter("province");
				String phoneNumber = request.getParameter("phone_number");
				String email = request.getParameter("email");
				String website = request.getParameter("website");
				String image = request.getParameter("image_link");
				Hotel newHotel = new Hotel(hotelId, hotelName, hotelAddress, province, phoneNumber, email, website, image);
				    try { 
				    	hotelDAO.addHotel(newHotel);
				    	response.getWriter().write("Thêm khách sạn thành công.");
				    } catch (SQLException e) {
				        e.printStackTrace();
				        response.getWriter().write("Lỗi khi thêm khách sạn.");
				    }
			}
	    	else if ("update".equals(action)) 
	    	{
	    		 try {
	    			 Hotel hotel = hotelDAO.getHotelById(hotelId);
	    			 	if (hotel == null) {
	    					response.sendError(HttpServletResponse.SC_NOT_FOUND);
	    					return;
	    				}
	    		String hotelName = request.getParameter("hotel_name");
	    		String hotelAddress = request.getParameter("hotel_address");
	    		String province = request.getParameter("province");
	    		String phoneNumber = request.getParameter("phone_number");
	    		String email = request.getParameter("email");
	    		String website = request.getParameter("website");
	    		String image = request.getParameter("image_link");
	    
	    		
	    		hotel.setHotelName(hotelName);
	    		hotel.setHotelAddress(hotelAddress);
	    		hotel.setProvince(province);
	    		hotel.setPhoneNumber(phoneNumber);
	    		hotel.setEmail(email);
	    		hotel.setWebsite(website);
	    		hotel.setImageLink(image);
	    		hotel.setHotelId(hotelId);
	    		hotelDAO.updateHotel(hotel);
	    		response.getWriter().write("Cập nhật khách sạn thành công.");
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 				 response.getWriter().write("Lỗi khi sửa thông tin khách sạn.");
	 			}
	    	}
	    	else if("delete".equals(action))
			 {
				 try {
					 	Hotel hotel = hotelDAO.getHotelById(hotelId);
	    			 	if (hotel == null) {
	    					response.sendError(HttpServletResponse.SC_NOT_FOUND);
	    					return;
	    				}
	    			 	hotelDAO.deleteHotel(hotel);
						response.getWriter().write("Xóa khách sạn thành công");
					} catch (SQLException e) {
						e.printStackTrace();
						response.getWriter().write("Lỗi khi xóa khách sạn");
					}
				}
	}
	
}



