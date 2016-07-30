package com.cardmanager.kdml.cardmanager;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

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

    private String card_Company;
    private String card_Tell_Num;
    private ArrayList<Cards> card_List;
    private int thisMonth;
    HashMap<String,Cards> map;
    public CardInfo(Cursor cs)
    {
        map = new HashMap<>(20, 0.8f);
        card_Company = cs.getString(cs.getColumnIndex("CARD_COMPANY"));
        card_Tell_Num = cs.getString(cs.getColumnIndex("CARD_TEL_NUM"));
        Calendar cal = Calendar.getInstance();
        thisMonth = cal.get(Calendar.MONTH)+1;

    }

    public void setSMSData(ContentResolver cr)
    {
        map.clear();
        String selection = "address = ?";
        String[] selectionArgs = {card_Tell_Num};
        String sortOrder = "date ASC";
        Cursor c = cr.query(Uri.parse("content://sms/inbox"), null, selection, selectionArgs, sortOrder);
        if(c.moveToFirst()){
            while(c.moveToNext()){
                String strAdd = c.getString(c.getColumnIndex("address"));
                getCardCostData(c,thisMonth,strAdd);
            }
        }
        DecimalFormat fmt=new DecimalFormat("##,###");
        Iterator<Cards> it = map.values().iterator();
        while(it.hasNext())
        {
            Cards cds = it.next();
            String sss = cds.getCard_Name()+" "+thisMonth+"¿ù »ç¿ë±Ý¾× : "+fmt.format(cds.getThis_Month_Cost())+"¿ø";
            Cards.idsArrList.add(sss);
        }

        c.close();
    }

    public void getCardCostData(Cursor c,int thisMonth,String strAdd) {
        try {
            Calendar cal = Calendar.getInstance();
            long origin = (long) Double.parseDouble(c.getString(c.getColumnIndex("date")));
            cal.setTimeInMillis(origin);
            int yyyy = cal.get(Calendar.YEAR);
            int MM = (cal.get(Calendar.MONTH) + 1);
            int dd = cal.get(Calendar.DATE);
            int HH = cal.get(Calendar.HOUR);
            int mm = cal.get(Calendar.MINUTE);
            int ss = cal.get(Calendar.SECOND);

            if (thisMonth == MM) {
                String str = c.getString(c.getColumnIndex("body"));
                if (strAdd.equals("15776200")) { // Çö´ë
                    String[] spl = str.split("\\n");
                    //Log.e("CardManagerClient", spl[0]+"-"+spl[1]+"-"+spl[2]+"-"+spl[3]);
                    //String won = spl[3].substring(0, spl[3].indexOf("¿ø")).replace(",", "");
                    String won = getNum(spl[3]);
                    String cardName = spl[1].substring(0, spl[1].indexOf(" "));
                    long sumWon = (long) Double.parseDouble(won);
                    if (map.containsKey(cardName)) {
                        Cards cds = map.get(cardName);
                        cds.setThis_Month_Cost(cds.getThis_Month_Cost() + sumWon);
                    } else {
                        Cards nCds = new Cards(cardName);
                        nCds.setThis_Month_Cost(nCds.getThis_Month_Cost() + sumWon);
                        map.put(cardName, nCds);
                    }
                }
                if (strAdd.equals("15447200")) { // ½ÅÇÑ
                    String[] spl2 = str.split(" ");
                    //Log.e("CardManagerClient", spl2[0]+"-"+spl2[1]+"-"+spl2[2]+"-"+spl2[3]+"-"+spl2[4]);
                    //String won = spl2[4].substring(spl2[4].indexOf(")") + 1, spl2[4].indexOf("¿ø")).replace(",", "");
                    String won = getNum(spl2[4]);
                    String cardName = "½ÅÇÑÄ«µå " + spl2[1];
                    long sumWon = (long) Double.parseDouble(won);
                    if (map.containsKey(cardName)) {
                        Cards cds = map.get(cardName);
                        cds.setThis_Month_Cost(cds.getThis_Month_Cost() + sumWon);
                    } else {
                        Cards nCds = new Cards(cardName);
                        nCds.setThis_Month_Cost(nCds.getThis_Month_Cost() + sumWon);
                        map.put(cardName, nCds);
                    }

                   // CustomerDatabase.getInstance(null).insertSMSData(cardName,c.getString(c.getColumnIndex("date")),won);
                }

                String date = yyyy + "/" + MM + "/" + dd + " " + HH + ":" + mm + ":" + ss + "";
                Log.d("shue", "body:" + str);
                Log.e("shue", "date(origin):" + c.getString(c.getColumnIndex("date")));
                Log.d("shue", "date:" + date);
                Log.d("shue", "type:" + c.getString(c.getColumnIndex("type")));
                Log.d("shue", "address:" + c.getString(c.getColumnIndex("address")));
            }
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
            {"Çö´ë","ZERO"},
            {"Çö´ë","X"},
            {"Çö´ë","X2"},
            {"Çö´ë","X3"},
            {"Çö´ë","M"},
            {"Çö´ë","M2"},
            {"Çö´ë","M3"},
            {"Çö´ë","RED"},
            {"Çö´ë","PURPLE"},
            {"Çö´ë","BLACK"},
            {"Çö´ë","T3"},
            {"Çö´ë","ÀÌ¸¶Æ® eÄ«µå"},
            {"Çö´ë","±âÅ¸Á¦ÈÞÄ«µå"},
            {"½ÅÇÑ","Cube"},
            {"½ÅÇÑ","Tasty"},
            {"½ÅÇÑ","Mr.Life"},
            {"½ÅÇÑ","ÁÖ°Å·¡½Å¿ë"},
            {"½ÅÇÑ","¹Ì·¡¼³°è"},
            {"½ÅÇÑ","Air Platinum"},
            {"½ÅÇÑ","Shopping"},
            {"½ÅÇÑ","B.Big"},
            {"½ÅÇÑ","LOVE"},
            {"½ÅÇÑ","The ACE"},
            {"½ÅÇÑ","BEST-F"},
            {"½ÅÇÑ","CLASSIC+"},
            {"½ÅÇÑ","LADY CLASSIC"},
            {"½ÅÇÑ","CLASSIC-Y"},
            {"½ÅÇÑ","RPM+ Platinum"},
            {"½ÅÇÑ","Cube Platinum"},
            {"½ÅÇÑ","Love Platinum"},
            {"½ÅÇÑ","Simple"},
            {"½ÅÇÑ","Simple Platinum"},
            {"½ÅÇÑ","Lesson Platinum"},
            {"½ÅÇÑ","23.5"},
            {"½ÅÇÑ","Hi-Point Nano F"},
            {"½ÅÇÑ","¾ÆÀÌÇàº¹(º¸À°·á)"},
            {"»ï¼º","±¹¹ÎÇàº¹(ÀÓ½Å,Ãâ»ê)"},
            {"»ï¼º","2v2"},
            {"»ï¼º","3v2 / 3+v2"},
            {"»ï¼º","4v2 / 4+v2 / BIZ"},
            {"»ï¼º","5v2"},
            {"»ï¼º","6v2 / BIZ"},
            {"»ï¼º","7v2 / 7+v2"},
            {"»ï¼º","THE 1 / ½ºÄ«ÀÌÆÐ½º / BIZ"},
            {"»ï¼º","¾Æ½Ã¾Æ³ª ¾Ö´ÏÆÐ½º"},
            {"»ï¼º","¾Æ¸Å¸®Ä­ ÀÍ½ºÇÁ·¹½º Platinum"},
            {"»ï¼º","THE O"},
            {"»ï¼º","¾Æ½Ã¾Æ³ª Áö¿£¹Ì"},
            {"»ï¼º","¾Æ¸ß½º °ñµå"},
            {"»ï¼º","¾Æ¸ß½º ºí·ç"},
            {"»ï¼º","½ºÄ«ÀÌÆÐ½º"},
            {"»ï¼º","½ºÆä¼È¸¶ÀÏ¸®Áö(½ºÄ«ÀÌÆÐ½º)"},
            {"»ï¼º","taptap S"},
            {"»ï¼º","taptap O"},
            {"KB±¹¹Î","±Âµ¥ÀÌ"},
            {"KB±¹¹Î","ÆÄÀÎÅ×Å©"},
            {"KB±¹¹Î","´Ù´ã"},
            {"KB±¹¹Î","°¡¿Â"},
            {"KB±¹¹Î","Ã»Ãá´ë·Î"},
            {"KB±¹¹Î","±Âµ¥ÀÌ¿Ã¸²"},
            {"KB±¹¹Î","´©¸®"},
            {"KB±¹¹Î","¿ÍÀÌÁî¿Ã¸²"},
            {"KB±¹¹Î","ÈÆ"},
            {"KB±¹¹Î","¹Î"},
            {"KB±¹¹Î","Á¤"},
            {"KB±¹¹Î","À½"},
            {"KB±¹¹Î","Çý´ã"},
            {"KB±¹¹Î","Çý´ã2"},
            {"KB±¹¹Î","¿ÍÀÌÁî"},
            {"KB±¹¹Î","±Â¼îÇÎ"},
            {"KB±¹¹Î","½ºÅ¸¸Æ½º"},
            {"KB±¹¹Î","¹Ì¸£"},
            {"KB±¹¹Î","¾ÆÀÌÇàº¹"},
            {"·Ôµ¥","µå¶óÀÌºùÆÐ½º"},
            {"·Ôµ¥","DCÆÐ½º"},
            {"·Ôµ¥","VEEX"},
            {"·Ôµ¥","¾ÆÀÌÇàº¹"},
            {"·Ôµ¥","¿Ã¸¶ÀÌ¼îÇÎ ±³Åë/Åë½Å/ÇØ¿Ü/Á¡½É"},
            {"·Ôµ¥","Æ÷ÀÎÆ®ÇÃ·¯½º"},
            {"·Ôµ¥","ÇÃ·¯½ºÆ÷ÅÙ"},
            {"·Ôµ¥","DC½´ÇÁ¸²"},
            {"·Ôµ¥","DC½º¸¶Æ®"},
            {"·Ôµ¥","ÅõÀÎ¿ø"},
            {"·Ôµ¥","½ºÄ«ÀÌÆÐ½º ·Ôµ¥°ñµå¾Æ¸ß½º"},
            {"·Ôµ¥","·Ôµ¥ µ¥ÀÏ¸®"},
            {"·Ôµ¥","º¤½º ÇÃ·¡Æ¼³Ñ"},
            {"·Ôµ¥","DCÅ¬¸¯"},
            {"·Ôµ¥","DCÇÃ·¯½º"},
            {"·Ôµ¥","DC½ºÀ§Æ®"},
            {"·Ôµ¥","¿ÉÆ¾ ÇÃ·¡Æ¼³Ñ"},
            {"·Ôµ¥","±¹¹ÎÇàº¹(ÀÓ½Å,Ãâ»ê)"},
            {"¾¾Æ¼","Å¬¸®¾î"},
            {"¾¾Æ¼","¸®¿öµå"},
            {"¾¾Æ¼","ÇÁ¸®¹Ì¾î¸¶ÀÏ"},
            {"¾¾Æ¼","ÇÁ¸®½ºÆ¼Áö"},
            {"¾¾Æ¼","¸ÖÆ¼ÇÃ·¯½º"},
            {"³óÇù","½º¸¶Æ¼"},
            {"³óÇù","º£ÀÌÁ÷"},
            {"³óÇù","¼¥ÇÎ / ¼¥ÇÎ+"},
            {"³óÇù","¿Ã¿ø"},
            {"³óÇù","½Ã·´"},
            {"³óÇù","Á¡Á¡"},
            {"³óÇù","Å×ÀÌÅ©5"},
            {"³óÇù","¾ÆÀÌÇàº¹(º¸À°·á)"},
            {"³óÇù","¼îÇÎ¼¼ÀÌºê"},
            {"³óÇù","ÇÏ³ª·Î"},
            {"³óÇù","·¹ÀÌµð ´Ù¼Ø"},
            {"³óÇù","ME / ME+"},
            {"³óÇù","±¹¹ÎÇàº¹(ÀÓ½Å,Ãâ»ê)"},
            {"³óÇù","À§"},
            {"ÇÏ³ª","Å¬·´ SK"},
            {"ÇÏ³ª","2X ¾ËÆÄ/°¨¸¶/½Ã±×¸¶"},
            {"ÇÏ³ª","ÅÍÄ¡¿ø"},
            {"ÇÏ³ª","ºñ¹ÙG"},
            {"ÇÏ³ª","Å©·Î½º¸¶ÀÏ"},
            {"ÇÏ³ª","½Ã±×´ÏÃ³"},
            {"ÇÏ³ª","POP"},
            {"ÇÏ³ª","¾ÆÀÌÇàº¹(º¸À°·á)"},
            {"ÇÏ³ª","½º¸¶Æ® DC"},
            {"ÇÏ³ª","¹Ì»ý"},
            {"ÇÏ³ª","»ýÈ°ÀÇ ´ÞÀÎ"},
            {"ÇÏ³ª","Sync"},
            {"ÇÏ³ª","ÇÏ³ª¸É¹ö½º 1Q"},
            {"ÇÏ³ª","ºòÆÌ"},
            {"ÇÏ³ª","½Ä»þ"},
            {"¿ì¸®","°¡"},
            {"¿ì¸®","³ª"},
            {"¿ì¸®","´Ù"},
            {"¿ì¸®","¶ó"},
            {"¿ì¸®","NEW ¿ì¸®V"},
            {"¿ì¸®","ºí·ç´ÙÀÌ¾Æ¸óµå"},
            {"¿ì¸®","·Î¾âºí·ç"},
            {"¿ì¸®","¾ÆÀÌÇàº¹(º¸À°·á)"}
    };


}
