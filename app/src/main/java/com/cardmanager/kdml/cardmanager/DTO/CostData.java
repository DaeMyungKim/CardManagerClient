package com.cardmanager.kdml.cardmanager.DTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 김대명 on 2016-08-08.
 */
public class CostData {
    private String cardName;
    private String yearMonth;
    private String cost;
    private String dateTimeOrigin;
    private String dateTime;
    private String email;
    private String name;
    private String phone;

    public CostData(){}

    public CostData(String dateTime, String cardName, String yearMonth, String cost, String dateTimeOrigin, String email, String name, String phone) {
        this.cardName = cardName;
        this.cost = cost;
        this.dateTime = dateTime;
        this.dateTimeOrigin = dateTimeOrigin;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.yearMonth = yearMonth;
    }

    public CostData(String dateTime, String cardName, String yearMonth, String cost, String dateTimeOrigin, String email) {
        this.dateTime = dateTime;
        this.cardName = cardName;
        this.yearMonth = yearMonth;
        this.cost = cost;
        this.dateTimeOrigin = dateTimeOrigin;
        this.email = email;


    }

    public CostData(String cardName, String yearMonth, String cost) {
        this.cardName = cardName;
        this.yearMonth = yearMonth;
        this.cost = cost;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDateTimeOrigin() {
        return dateTimeOrigin;
    }

    public void setDateTimeOrigin(String dateTimeOrigin) {
        this.dateTimeOrigin = dateTimeOrigin;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
