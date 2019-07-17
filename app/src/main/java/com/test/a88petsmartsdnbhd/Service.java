package com.test.a88petsmartsdnbhd;

public class Service {

    String uid;
    String serviceName;
    String serviceStatus;
    String serviceDate;
    String userUID;

    public Service(String uid, String serviceName, String serviceStatus, String serviceDate, String userUID) {
        this.uid = uid;
        this.serviceName = serviceName;
        this.serviceStatus = serviceStatus;
        this.serviceDate = serviceDate;
        this.userUID = userUID;
    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public Service(){

    }




}
