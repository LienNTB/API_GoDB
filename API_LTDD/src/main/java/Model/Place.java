package Model;

public class Place {
    private String placeId;
    private String placeName;
    private String dec;
    private String contents;
    private String imageLink;

    public Place() {}
    
    public Place(String placeId, String placeName, String dec, String contents, String imageLink) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.dec = dec;
        this.contents = contents;
        this.imageLink = imageLink;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
