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
            String sss = cds.getCard_Name()+" "+thisMonth+"�� ���ݾ� : "+fmt.format(cds.getThis_Month_Cost())+"��";
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
                if (strAdd.equals("15776200")) { // ����
                    String[] spl = str.split("\\n");
                    //Log.e("CardManagerClient", spl[0]+"-"+spl[1]+"-"+spl[2]+"-"+spl[3]);
                    //String won = spl[3].substring(0, spl[3].indexOf("��")).replace(",", "");
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
                if (strAdd.equals("15447200")) { // ����
                    String[] spl2 = str.split(" ");
                    //Log.e("CardManagerClient", spl2[0]+"-"+spl2[1]+"-"+spl2[2]+"-"+spl2[3]+"-"+spl2[4]);
                    //String won = spl2[4].substring(spl2[4].indexOf(")") + 1, spl2[4].indexOf("��")).replace(",", "");
                    String won = getNum(spl2[4]);
                    String cardName = "����ī�� " + spl2[1];
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
            {"����","ZERO"},
            {"����","X"},
            {"����","X2"},
            {"����","X3"},
            {"����","M"},
            {"����","M2"},
            {"����","M3"},
            {"����","RED"},
            {"����","PURPLE"},
            {"����","BLACK"},
            {"����","T3"},
            {"����","�̸�Ʈ eī��"},
            {"����","��Ÿ����ī��"},
            {"����","Cube"},
            {"����","Tasty"},
            {"����","Mr.Life"},
            {"����","�ְŷ��ſ�"},
            {"����","�̷�����"},
            {"����","Air Platinum"},
            {"����","Shopping"},
            {"����","B.Big"},
            {"����","LOVE"},
            {"����","The ACE"},
            {"����","BEST-F"},
            {"����","CLASSIC+"},
            {"����","LADY CLASSIC"},
            {"����","CLASSIC-Y"},
            {"����","RPM+ Platinum"},
            {"����","Cube Platinum"},
            {"����","Love Platinum"},
            {"����","Simple"},
            {"����","Simple Platinum"},
            {"����","Lesson Platinum"},
            {"����","23.5"},
            {"����","Hi-Point Nano F"},
            {"����","�����ູ(������)"},
            {"�Ｚ","�����ູ(�ӽ�,���)"},
            {"�Ｚ","2v2"},
            {"�Ｚ","3v2 / 3+v2"},
            {"�Ｚ","4v2 / 4+v2 / BIZ"},
            {"�Ｚ","5v2"},
            {"�Ｚ","6v2 / BIZ"},
            {"�Ｚ","7v2 / 7+v2"},
            {"�Ｚ","THE 1 / ��ī���н� / BIZ"},
            {"�Ｚ","�ƽþƳ� �ִ��н�"},
            {"�Ｚ","�ƸŸ�ĭ �ͽ������� Platinum"},
            {"�Ｚ","THE O"},
            {"�Ｚ","�ƽþƳ� ������"},
            {"�Ｚ","�Ƹ߽� ���"},
            {"�Ｚ","�Ƹ߽� ���"},
            {"�Ｚ","��ī���н�"},
            {"�Ｚ","����ȸ��ϸ���(��ī���н�)"},
            {"�Ｚ","taptap S"},
            {"�Ｚ","taptap O"},
            {"KB����","�µ���"},
            {"KB����","������ũ"},
            {"KB����","�ٴ�"},
            {"KB����","����"},
            {"KB����","û����"},
            {"KB����","�µ��̿ø�"},
            {"KB����","����"},
            {"KB����","������ø�"},
            {"KB����","��"},
            {"KB����","��"},
            {"KB����","��"},
            {"KB����","��"},
            {"KB����","����"},
            {"KB����","����2"},
            {"KB����","������"},
            {"KB����","�¼���"},
            {"KB����","��Ÿ�ƽ�"},
            {"KB����","�̸�"},
            {"KB����","�����ູ"},
            {"�Ե�","����̺��н�"},
            {"�Ե�","DC�н�"},
            {"�Ե�","VEEX"},
            {"�Ե�","�����ູ"},
            {"�Ե�","�ø��̼��� ����/���/�ؿ�/����"},
            {"�Ե�","����Ʈ�÷���"},
            {"�Ե�","�÷�������"},
            {"�Ե�","DC������"},
            {"�Ե�","DC����Ʈ"},
            {"�Ե�","���ο�"},
            {"�Ե�","��ī���н� �Ե����Ƹ߽�"},
            {"�Ե�","�Ե� ���ϸ�"},
            {"�Ե�","���� �÷�Ƽ��"},
            {"�Ե�","DCŬ��"},
            {"�Ե�","DC�÷���"},
            {"�Ե�","DC����Ʈ"},
            {"�Ե�","��ƾ �÷�Ƽ��"},
            {"�Ե�","�����ູ(�ӽ�,���)"},
            {"��Ƽ","Ŭ����"},
            {"��Ƽ","������"},
            {"��Ƽ","�����̾��"},
            {"��Ƽ","������Ƽ��"},
            {"��Ƽ","��Ƽ�÷���"},
            {"����","����Ƽ"},
            {"����","������"},
            {"����","���� / ����+"},
            {"����","�ÿ�"},
            {"����","�÷�"},
            {"����","����"},
            {"����","����ũ5"},
            {"����","�����ູ(������)"},
            {"����","���μ��̺�"},
            {"����","�ϳ���"},
            {"����","���̵� �ټ�"},
            {"����","ME / ME+"},
            {"����","�����ູ(�ӽ�,���)"},
            {"����","��"},
            {"�ϳ�","Ŭ�� SK"},
            {"�ϳ�","2X ����/����/�ñ׸�"},
            {"�ϳ�","��ġ��"},
            {"�ϳ�","���G"},
            {"�ϳ�","ũ�ν�����"},
            {"�ϳ�","�ñ״�ó"},
            {"�ϳ�","POP"},
            {"�ϳ�","�����ູ(������)"},
            {"�ϳ�","����Ʈ DC"},
            {"�ϳ�","�̻�"},
            {"�ϳ�","��Ȱ�� ����"},
            {"�ϳ�","Sync"},
            {"�ϳ�","�ϳ��ɹ��� 1Q"},
            {"�ϳ�","����"},
            {"�ϳ�","�Ļ�"},
            {"�츮","��"},
            {"�츮","��"},
            {"�츮","��"},
            {"�츮","��"},
            {"�츮","NEW �츮V"},
            {"�츮","�����̾Ƹ��"},
            {"�츮","�ξ���"},
            {"�츮","�����ູ(������)"}
    };


}
