package com.cardmanager.kdml.cardmanager.DTO;

/**
 * Created by 김대명 on 2016-08-08.
 */
public class CostData {
    private String cardName;
    private String yearMonth;
    private String cost;

    public CostData(String cardName, String yearMonth, String cost) {
        this.cardName = cardName;
        this.yearMonth = yearMonth;
        this.cost = cost;
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
}
