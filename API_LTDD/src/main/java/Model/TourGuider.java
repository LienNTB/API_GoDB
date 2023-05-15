package Model;

import java.math.BigDecimal;
import java.util.Date;


public class TourGuider {
    private String tourGuiderId;
    private String fullName;
    private Date birthDay;
    private boolean gender;
    private String address;
    private String tel;
    private String email;
    private String imageLink;
    private String cic;
    private BigDecimal salary;

    public TourGuider(String tourGuiderId, String fullName, Date birthDay, boolean gender, String address, String tel, String email, String imageLink, String cic, BigDecimal salary) {
        this.tourGuiderId = tourGuiderId;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.address = address;
        this.tel = tel;
        this.email = email;
        this.imageLink = imageLink;
        this.cic = cic;
        this.salary = salary;
    }

    public String getTourGuiderId() {
        return tourGuiderId;
    }

    public void setTourGuiderId(String tourGuiderId) {
        this.tourGuiderId = tourGuiderId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getCic() {
        return cic;
    }

    public void setCic(String cic) {
        this.cic = cic;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
