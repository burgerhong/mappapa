package com.example.jason.mappapa;

public class temporaryDetails {
    private String requester;
    private double latitude;
    private double longitude;
    private String slotID;

    public temporaryDetails() {
    }

    public String getSlotID() {
        return slotID;
    }

    public String getRequester() {
        return requester;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public temporaryDetails (String requester,double latitude, double longitude,String slotID){
        this.requester = requester;
        this.latitude = latitude;
        this.longitude = longitude;
        this.slotID = slotID;
    }

}
