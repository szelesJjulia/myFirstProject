package com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist.data;

/**
 * Created by szeles.julide on 2017/06/14.
 */

public class MarkerObject {

    private String markerTitle;
    private String markerComment;
    private double markerLatitude;
    private double markerLongtitude;
    private int _id;

    public MarkerObject(String title, String comment, double lat, double longt){
        this.markerTitle = title;
        this.markerComment = comment;
        this.markerLatitude = lat;
        this.markerLongtitude = longt;
    }

    public MarkerObject(){

    }

    public String getMarkerTitle() {
        return markerTitle;
    }

    public void setMarkerTitle(String markerTitle) {
        this.markerTitle = markerTitle;
    }

    public String getMarkerComment() {
        return markerComment;
    }

    public void setMarkerComment(String markerComment) {
        this.markerComment = markerComment;
    }

    public double getMarkerLatitude() {
        return markerLatitude;
    }

    public void setMarkerLatitude(double markerLatitude) {
        this.markerLatitude = markerLatitude;
    }

    public double getMarkerLongtitude() {
        return markerLongtitude;
    }

    public void setMarkerLongtitude(double markerLongtitude) {
        this.markerLongtitude = markerLongtitude;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
