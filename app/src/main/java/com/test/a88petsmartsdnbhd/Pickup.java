package com.test.a88petsmartsdnbhd;

public class Pickup {


    String uid;
    String pickupTime;
    String pickupLatitude;
    String pickupLongitude;
    String pickupLocation;
    String userUid;

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }



    public String getPickupLatitude() {
        return pickupLatitude;
    }

    public Pickup(String uid, String pickupTime, String pickupLatitude, String pickupLongitude, String pickupLocation, String userUid) {
        this.uid = uid;
        this.pickupTime = pickupTime;
        this.pickupLatitude = pickupLatitude;
        this.pickupLongitude = pickupLongitude;
        this.pickupLocation = pickupLocation;
        this.userUid = userUid;
    }

    public void setPickupLatitude(String pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public String getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(String pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }



    public Pickup(){

    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

}
