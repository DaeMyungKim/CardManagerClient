package com.cardmanager.kdml.cardmanager.DTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<CostData> test;
    private Map<String,CostData> maptest;
    public CostData(){}

    public CostData(String dateTime, String cardName, String yearMonth, String cost, String dateTimeOrigin,String email) {
        this.dateTime = dateTime;
        this.cardName = cardName;
        this.yearMonth = yearMonth;
        this.cost = cost;
        this.dateTimeOrigin = dateTimeOrigin;
        this.email = email;

        test = new ArrayList<>();

        test.add( new CostData("test1","test1","test1"));
        test.add( new CostData("test2","test2","test2"));

        maptest = new HashMap<>();

        maptest.put("201608",new CostData("test1","test1","test1"));
        maptest.put("201607",new CostData("test2","test2","test2"));


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

    public List<CostData> getTest() {
        return test;
    }

    public void setTest(List<CostData> test) {
        this.test = test;
    }

    public Map<String, CostData> getMaptest() {
        return maptest;
    }

    public void setMaptest(Map<String, CostData> maptest) {
        this.maptest = maptest;
    }
}
