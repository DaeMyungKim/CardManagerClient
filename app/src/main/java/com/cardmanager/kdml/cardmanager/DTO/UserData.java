package com.cardmanager.kdml.cardmanager.DTO;

/**
 * Created by 김대명 on 2016-08-30.
 */
public class UserData {

    private String name;
    private String email;
    private String phone;
    private String fbid;

    public UserData(String email, String fbid, String name, String phone) {
        this.email = email;
        this.fbid = fbid;
        this.name = name;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
