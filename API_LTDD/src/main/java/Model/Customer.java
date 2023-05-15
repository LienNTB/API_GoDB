package Model;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Customer {
    private String customerId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String imageLink;
    private String address;
    private boolean gender;
    private Date birthDay;

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public String getBirthDay() throws ParseException {
		 if (birthDay != null) {
		        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
		        return outputFormat.format(birthDay);
		    }
		    return "";
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public Customer(String customerId, String fullName, String email, String phoneNumber, String imageLink,
			String address, boolean gender, Date birthDay) {
		super();
		this.customerId = customerId;
		this.fullName = fullName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.imageLink = imageLink;
		this.address = address;
		this.gender = gender;
		this.birthDay = birthDay;
	}

}
