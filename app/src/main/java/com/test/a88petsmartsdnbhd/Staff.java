package com.test.a88petsmartsdnbhd;

public class Staff {

    String uid;
    String name;
    String address;
    String email;
    String phoneNum;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    public Staff(String uid, String name, String address, String email, String phoneNum) {
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public Staff() {
    }


}
