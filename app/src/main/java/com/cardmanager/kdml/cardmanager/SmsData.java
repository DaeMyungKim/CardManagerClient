package com.cardmanager.kdml.cardmanager;

/**
 * Created by 김대명 on 2016-08-04.
 */
public class SmsData {

    private double dataTime;
    private double dateTimeConvert;
    private String text;
    private double cost;
    private String type;
    private String company;
    private String cardName;

    public double getDataTime() {
        return dataTime;
    }

    public void setDataTime(double dataTime) {
        this.dataTime = dataTime;
    }

    public double getDateTimeConvert() {
        return dateTimeConvert;
    }

    public void setDateTimeConvert(double dateTimeConvert) {
        this.dateTimeConvert = dateTimeConvert;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
