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
    public User(String _email)
    {
        email = _email;
    }
}
