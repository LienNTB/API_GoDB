	package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Staff {
    private String staffId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private boolean gender;
    private Date birthDay;
    private String cic;
    private String address;
    private String imageLink;

    public Staff(String staffId, String fullName, String email, String phoneNumber, boolean gender,Date birthDay, String cic, String address, String imageLink) {
        super();
    	this.staffId = staffId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDay = birthDay;
        this.cic = cic;
        this.address = address;
        this.imageLink = imageLink;
    }


	public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
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

    public String getCic() {
        return cic;
    }

    public void setCic(String cic) {
        this.cic = cic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}