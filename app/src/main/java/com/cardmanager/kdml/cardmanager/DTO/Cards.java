package com.cardmanager.kdml.cardmanager.DTO;

import com.cardmanager.kdml.cardmanager.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kdml on 2016-06-16.
 */
public class Cards {










    private String card_Name;
    private long this_Month_Cost;
    public static final String[] sCheeseStrings = {
            "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam"
    };
    public static ArrayList<String> idsArrList = new ArrayList<String>();
    private static final Random RANDOM = new Random();

    public Cards(String _card_Name)
    {
        card_Name = _card_Name;
        this_Month_Cost= 0; // asdf
    }

    public String toString(String thisMonth)
    {
        DecimalFormat fmt=new DecimalFormat("##,###");
        return card_Name+" " +thisMonth+"월 사용금액 : "+fmt.format(""+this_Month_Cost)+"원";
    }


    public static int getRandomCheeseDrawable() {
        switch (RANDOM.nextInt(5)) {
            default:
            case 0:
                return R.drawable.cheese_1;
            case 1:
                return R.drawable.cheese_2;
            case 2:
                return R.drawable.cheese_3;
            case 3:
                return R.drawable.cheese_4;
            case 4:
                return R.drawable.cheese_5;
        }
    }


    public String getCard_Name() {
        return card_Name;
    }

    public void setCard_Name(String card_Name) {
        this.card_Name = card_Name;
    }

    public long getThis_Month_Cost() {
        return this_Month_Cost;
    }
    public String getThis_Month_Cost_String() {
        return String.valueOf( this_Month_Cost);
    }

    public void setThis_Month_Cost(long this_Month_Cost) {
        this.this_Month_Cost = this_Month_Cost;
    }
}
