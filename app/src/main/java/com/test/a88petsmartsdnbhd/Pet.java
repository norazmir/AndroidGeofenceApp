package com.test.a88petsmartsdnbhd;

public class Pet {
    String uid;
    String petName;
    String petType;
    String petAge;
    String userUid;

    public Pet(){

    }

    public Pet(String uid, String petName, String petType, String petAge, String userUid) {
        this.uid = uid;
        this.petName = petName;
        this.petType = petType;
        this.petAge = petAge;
        this.userUid = userUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }


    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }




}
