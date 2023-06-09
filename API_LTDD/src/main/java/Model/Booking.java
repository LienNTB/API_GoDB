package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {
    private String bookingId;
    private String customerId;
    private String staffId;
    private String vehicleId;
    private String tourId;
    private String hotelId;
    private Date bookingDate;
    private String bookingType;

    public Booking(String bookingId, String customerId, String staffId, String vehicleId, String tourId, String hotelId, Date bookingDate, String bookingType) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.vehicleId = vehicleId;
        this.tourId = tourId;
        this.hotelId = hotelId;
        this.bookingDate = bookingDate;
        this.bookingType = bookingType;
        
        if(vehicleId == "")
		{
			this.vehicleId = null;
		}
        else if(tourId == "")
		{
			this.tourId = null;
		}
        else if(hotelId == "")
        {
        	this.hotelId=null;
        }
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getBookingDate() throws ParseException 
    {
		 if (bookingDate != null) {
		        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
		        return outputFormat.format(bookingDate);
		    }
		    return "";
	}
    
    public void checkType()
	{
        if(vehicleId == "")
		{
			this.vehicleId = null;
		}
        else if(tourId == "")
		{
			this.tourId = null;
		}
        else if(hotelId=="")
        {
        	this.hotelId=null;
        }
	}
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }
}