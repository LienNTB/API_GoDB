package Model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Tour {
    private String tourId;
    private String tourGuiderId;
    private String placeId;
    private String tourName;
    private String placeGo;
    private Date dateGo;
    private Date dateBack;
    private int numPerson;
    private BigDecimal price;
    private String imageLink;
    private boolean state;

    public Tour(String tourId, String tourGuiderId, String placeId, String tourName, String placeGo, Date dateGo, Date dateBack, int numPerson, BigDecimal price, String imageLink, boolean state) {
        this.tourId = tourId;
        this.tourGuiderId = tourGuiderId;
        this.placeId = placeId;
        this.tourName = tourName;
        this.placeGo = placeGo;
        this.dateGo = dateGo;
        this.dateBack = dateBack;
        this.numPerson = numPerson;
        this.price = price;
        this.imageLink = imageLink;
        this.state = state;
    }


	public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getTourGuiderId() {
        return tourGuiderId;
    }

    public void setTourGuiderId(String tourGuiderId) {
        this.tourGuiderId = tourGuiderId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getPlaceGo() {
        return placeGo;
    }

    public void setPlaceGo(String placeGo) {
        this.placeGo = placeGo;
    }


    public void setDateGo(Date dateGo) {
        this.dateGo = dateGo;
    }



    public void setDateBack(Date dateBack) {
        this.dateBack = dateBack;
    }

    public int getNumPerson() {
        return numPerson;
    }

    public void setNumPerson(int numPerson) {
        this.numPerson = numPerson;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
   
	public String getDateGo() throws ParseException {
		 if (dateGo != null) {
		        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
		        return outputFormat.format(dateGo);
		    }
		    return "";
	}
	public String getDateBack() throws ParseException {
		 if (dateBack != null) {
		        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
		        return outputFormat.format(dateBack);
		    }
		    return "";
	}
}
