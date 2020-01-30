package edu.sp.spgryphons;

public class eventObj {
    private String title;
    private String date;
    private String time;
    private String description;
    private double latitude;
    private double longitude;

    public eventObj(String title, String desc) {

    }

    public eventObj(String title, String date, String time, String desc, double lat, double longi){

        this.title = title;
        this.date = date;
        this.time = time;
        this.description = desc;
        this.longitude = longi;
        this.latitude = lat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLong() {
        return longitude;
    }

    public void setLong(double longi) {
        this.longitude = longi;
    }

    public double getLat() {
        return latitude;
    }

    public void setLat(double lat) {
        this.latitude = lat;
    }
}
