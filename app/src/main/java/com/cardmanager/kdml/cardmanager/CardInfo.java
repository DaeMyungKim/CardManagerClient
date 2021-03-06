package com.cardmanager.kdml.cardmanager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.cardmanager.kdml.cardmanager.DTO.Cards;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by kdml on 2016-06-20.
 */
public class CardInfo {

    public String getCard_Company() {
        return card_Company;
    }
    public void setCard_Company(String card_Company) {
        this.card_Company = card_Company;
    }
    public ArrayList<Cards> getCard_List() {
        return card_List;
    }
    public void setCard_List(ArrayList<Cards> card_List) {
        this.card_List = card_List;
    }
    public ArrayList<String> getAl() {return al;}
    public void setAl(ArrayList<String> al) {this.al = al;}
    public String getCardName() {return cardName;}
    public void setCardName(String cardName) {this.cardName = cardName;}
    public long getMonthlyCost() {return monthlyCost;}
    public void setMonthlyCost(long monthlyCost) {this.monthlyCost = monthlyCost;}
    public String getMonthlyCostStr() {return monthlyCostStr;}
    public void setMonthlyCostStr(String monthlyCostStr) {this.monthlyCostStr = monthlyCostStr;}
    public int getThisMonth() {
        return thisMonth;
    }
    public void setThisMonth(int thisMonth) {
        this.thisMonth = thisMonth;
    }
    public ArrayList<String> al;
    private String card_Company;
    private String card_Tell_Num;
    private long monthlyCost;
    private String cardName;
    private String monthlyCostStr;
    private ArrayList<Cards> card_List;



    private int thisMonth;

    public CardInfo(Cursor cs)
    {

        card_Company = cs.getString(cs.getColumnIndex("CARD_COMPANY"));
        card_Tell_Num = cs.getString(cs.getColumnIndex("CARD_TEL_NUM"));
        Calendar cal = Calendar.getInstance();
        thisMonth = cal.get(Calendar.MONTH)+1;

    }

    public void setSMSData(ContentResolver cr)
    {
        String selection = "address = ?";
        String[] selectionArgs = {card_Tell_Num};
        String sortOrder = "date ASC";
        // SMS 문자 데이터를 전부 읽어옴
        Cursor c = cr.query(Uri.parse("content://sms/inbox"), null, selection, selectionArgs, sortOrder);
        if(c.moveToFirst()){
            while(c.moveToNext()){
                String strAdd = c.getString(c.getColumnIndex("address"));
                getCardCostData(c,thisMonth,strAdd);
            }
        }
        c.close();
    }

    public void getCardCostData(Cursor c,int thisMonth,String strAdd) {
        try {

            String date = c.getString(c.getColumnIndex("date"));
            long origin = (long) Double.parseDouble(date);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(origin);
            int yyyy = cal.get(Calendar.YEAR);
            int MM = (cal.get(Calendar.MONTH) + 1);
            int dd = cal.get(Calendar.DATE);
            int HH = cal.get(Calendar.HOUR);
            int mm = cal.get(Calendar.MINUTE);
            int ss = cal.get(Calendar.SECOND);
            String cardName = "",won = "";
            String str = c.getString(c.getColumnIndex("body"));
            if (strAdd.equals("15776200")) { // 현대카드
                String[] spl = str.split("\\n");
                won = getNum(spl[3].split(" ")[0]);
                cardName = al.get(2) + spl[1].substring(0, spl[1].indexOf(" "));
            }
            if (strAdd.equals("15447200")) { // 신한카드
                String[] spl2 = str.split(" ");
                won = getNum(spl2[4]);
                cardName = al.get(4) + spl2[1];
            }

            String dateConvert = yyyy + "" + MM + "" + dd + " " + HH + ":" + mm + ":" + ss + "";
            Log.d("shue", "body:" + str);
            Log.d("shue", "date(origin):" + date);
            Log.d("shue", "date:" + dateConvert);
            Log.d("shue", "type:" + c.getString(c.getColumnIndex("type")));
            Log.d("shue", "address:" + c.getString(c.getColumnIndex("address")));
            ContentValues values = new ContentValues();
            values.put("cardName",cardName);
            values.put("dataTime",date);
            values.put("cost",won);
            values.put("text",str);
            values.put("dateTimeConvert",dateConvert);
            values.put("company",strAdd);
            values.put("year",yyyy);
            values.put("month",MM);
            values.put("day",dd);
            CustomerDatabase.getInstance(null).insertSMSData(values);


        } catch (Exception ex) {
            Log.e("CardManagerClient", "Exception in getCardCostData()", ex);
        }
    }

    public String getNum(String strs)
    {
        String won="";
        for (int i = 0; i < strs.length(); i++) {
            char ch = strs.charAt(i);
            if ("0123456789".contains(ch + ""))
                won += strs.charAt(i);
        }
        return won;
    }

    public static final String[][] cardInfo= {
            {"현대","ZERO"},
            {"현대","X"},
            {"현대","X2"},
            {"현대","X3"},
            {"현대","M"},
            {"현대","M2"},
            {"현대","M3"},
            {"현대","RED"},
            {"현대","PURPLE"},
            {"현대","BLACK"},
            {"현대","T3"},
            {"현대","이마트 e카드"},
            {"현대","기타제휴카드"},
            {"신한","Cube"},
            {"신한","Tasty"},
            {"신한","Mr.Life"},
            {"신한","주거래신용"},
            {"신한","미래설계"},
            {"신한","Air Platinum"},
            {"신한","Shopping"},
            {"신한","B.Big"},
            {"신한","LOVE"},
            {"신한","The ACE"},
            {"신한","BEST-F"},
            {"신한","CLASSIC+"},
            {"신한","LADY CLASSIC"},
            {"신한","CLASSIC-Y"},
            {"신한","RPM+ Platinum"},
            {"신한","Cube Platinum"},
            {"신한","Love Platinum"},
            {"신한","Simple"},
            {"신한","Simple Platinum"},
            {"신한","Lesson Platinum"},
            {"신한","23.5"},
            {"신한","Hi-Point Nano F"},
            {"신한","아이행복(보육료)"},
            {"삼성","국민행복(임신,출산)"},
            {"삼성","2v2"},
            {"삼성","3v2 / 3+v2"},
            {"삼성","4v2 / 4+v2 / BIZ"},
            {"삼성","5v2"},
            {"삼성","6v2 / BIZ"},
            {"삼성","7v2 / 7+v2"},
            {"삼성","THE 1 / 스카이패스 / BIZ"},
            {"삼성","아시아나 애니패스"},
            {"삼성","아매리칸 익스프레스 Platinum"},
            {"삼성","THE O"},
            {"삼성","아시아나 지엔미"},
            {"삼성","아멕스 골드"},
            {"삼성","아멕스 블루"},
            {"삼성","스카이패스"},
            {"삼성","스페셜마일리지(스카이패스)"},
            {"삼성","taptap S"},
            {"삼성","taptap O"},
            {"KB국민","굿데이"},
            {"KB국민","파인테크"},
            {"KB국민","다담"},
            {"KB국민","가온"},
            {"KB국민","청춘대로"},
            {"KB국민","굿데이올림"},
            {"KB국민","누리"},
            {"KB국민","와이즈올림"},
            {"KB국민","훈"},
            {"KB국민","민"},
            {"KB국민","정"},
            {"KB국민","음"},
            {"KB국민","혜담"},
            {"KB국민","혜담2"},
            {"KB국민","와이즈"},
            {"KB국민","굿쇼핑"},
            {"KB국민","스타맥스"},
            {"KB국민","미르"},
            {"KB국민","아이행복"},
            {"롯데","드라이빙패스"},
            {"롯데","DC패스"},
            {"롯데","VEEX"},
            {"롯데","아이행복"},
            {"롯데","올마이쇼핑 교통/통신/해외/점심"},
            {"롯데","포인트플러스"},
            {"롯데","플러스포텐"},
            {"롯데","DC슈프림"},
            {"롯데","DC스마트"},
            {"롯데","투인원"},
            {"롯데","스카이패스 롯데골드아멕스"},
            {"롯데","롯데 데일리"},
            {"롯데","벡스 플래티넘"},
            {"롯데","DC클릭"},
            {"롯데","DC플러스"},
            {"롯데","DC스위트"},
            {"롯데","옵틴 플래티넘"},
            {"롯데","국민행복(임신,출산)"},
            {"씨티","클리어"},
            {"씨티","리워드"},
            {"씨티","프리미어마일"},
            {"씨티","프리스티지"},
            {"씨티","멀티플러스"},
            {"농협","스마티"},
            {"농협","베이직"},
            {"농협","샵핑 / 샵핑+"},
            {"농협","올원"},
            {"농협","시럽"},
            {"농협","점점"},
            {"농협","테이크5"},
            {"농협","아이행복(보육료)"},
            {"농협","쇼핑세이브"},
            {"농협","하나로"},
            {"농협","레이디 다솜"},
            {"농협","ME / ME+"},
            {"농협","국민행복(임신,출산)"},
            {"농협","위"},
            {"하나","클럽 SK"},
            {"하나","2X 알파/감마/시그마"},
            {"하나","터치원"},
            {"하나","비바G"},
            {"하나","크로스마일"},
            {"하나","시그니처"},
            {"하나","POP"},
            {"하나","아이행복(보육료)"},
            {"하나","스마트 DC"},
            {"하나","미생"},
            {"하나","생활의 달인"},
            {"하나","Sync"},
            {"하나","하나맴버스 1Q"},
            {"하나","빅팟"},
            {"하나","식샤"},
            {"우리","가"},
            {"우리","나"},
            {"우리","다"},
            {"우리","라"},
            {"우리","NEW 우리V"},
            {"우리","블루다이아몬드"},
            {"우리","로얄블루"},
            {"우리","아이행복(보육료)"}
    };



}
