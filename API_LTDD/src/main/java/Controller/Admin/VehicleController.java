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


import DAO.VehicleDAO;
import DBConnect.DBConnect;
import Model.Vehicle;

@WebServlet(urlPatterns = "/admin/vehicles")
public class VehicleController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VehicleDAO vehicleDAO;
	private Connection conn;
	public void init() throws ServletException {
		
		try {
			DBConnect DbConnect = new DBConnect();
			this.conn = DbConnect.getConnection();
			vehicleDAO = new VehicleDAO(conn);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	/**
	 * 
	 */
	public VehicleController() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("application/json;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");

		List<Vehicle> vehicles = null;
		try {
			vehicles = vehicleDAO.getAllVehicles();
			ObjectMapper obj = new ObjectMapper();
			obj.writeValue(resp.getOutputStream(),vehicles);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("application/json;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		String vehicleId = req.getParameter("vehicle_id");

		if ("search".equals(action)) {
			try {
				Vehicle vehicle  = (Vehicle) vehicleDAO.getVehicleById(vehicleId);
				if (vehicle != null) {
					ObjectMapper obj = new ObjectMapper();
					obj.writeValue(resp.getOutputStream(), vehicle);
				} else {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
		else if("add".equals(action)) {
			String rentalCompany = req.getParameter("rental_company");
			String Model = req.getParameter("model");
			String Colder = req.getParameter("coler");
			String licensePlate = req.getParameter("license_plate");
			//BigDecimalr rentalRate = 
			Boolean available = Boolean.parseBoolean(req.getParameter("available"));
			
			Vehicle newvehicle = new Vehicle(vehicleId, rentalCompany, Model, Colder, licensePlate, null, false);
			try {
				vehicleDAO.addVehicle(newvehicle);
				resp.getWriter().write("Thêm phương tiên thành công");
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
				resp.getWriter().write("Lỗi khi thêm phương tiện");
				System.out.println("Lỗi thêm phương tiện");
			}
		}
		else if("update".equals(action)) {
			try {
				Vehicle vehicle = (Vehicle) vehicleDAO.getVehicleById(vehicleId);
				if (vehicle == null) {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
				String rentalCompany = req.getParameter("rental_company");
				String Model = req.getParameter("model");
				String Coler = req.getParameter("coler");
				String licensePlate = req.getParameter("license_plate");
				//BigDecimalr rentalRate = 
				Boolean available = Boolean.parseBoolean(req.getParameter("available"));
				
				vehicle.setRentalCompany(rentalCompany);
				vehicle.setModel(Model);
				vehicle.setColor(Coler);
				vehicle.setLicensePlate(licensePlate);
				vehicle.setRentalRate(null);
				vehicle.setAvailable(false);
				
				vehicleDAO.updateVehicle(vehicle);
				resp.getWriter().write("Cập nhật thành công");
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resp.getWriter().write("Lỗi khi cập nhật");
			}
			
			
		}
		
	}


}
