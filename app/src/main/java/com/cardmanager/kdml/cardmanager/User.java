package com.cardmanager.kdml.cardmanager;

/**
 * Created by 김대명사무실 on 2016-07-29.
 */
public class User {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String email;
    public String name;
    public String FireBase_ID;

    public String getLastSMSReadDate() {
        return lastSMSReadDate;
    }

    public void setLastSMSReadDate(String lastSMSReadDate) {
        this.lastSMSReadDate = lastSMSReadDate;
    }

    public String getFireBase_ID() {
        return FireBase_ID;
    }

    public void setKey(String FireBase_ID) {
        this.FireBase_ID = FireBase_ID;
    }

    public String lastSMSReadDate;
    public User(){}
    public User(String _email)
    {
        email = _email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
