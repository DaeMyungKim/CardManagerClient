package com.cardmanager.kdml.cardmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.cardmanager.kdml.cardmanager.DTO.Cards;
import com.cardmanager.kdml.cardmanager.DTO.Cost;
import com.cardmanager.kdml.cardmanager.DTO.CostData;
import com.cardmanager.kdml.cardmanager.DTO.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kdml on 2016-06-19.
 */
public class CustomerDatabase {
    //String CustomerName;
    //String CustomerEmain;
    User user;
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    int lastSMSTime;
    private ArrayList<CardInfo> cardInfoArrayList;

    public int getLastSMSTime() {return lastSMSTime;}
    public void setLastSMSTime(int lastSMSTime) {this.lastSMSTime = lastSMSTime;}
    /*public String getCustomerEmain() {
        return CustomerEmain;
    }
    public void setCustomerEmain(String customerEmain) {
        CustomerEmain = customerEmain;
    }
    public String getCustomerName() {
        return CustomerName;
    }
    public void setCustomerName(String customerName) {CustomerName = customerName;}*/
    public ArrayList<CardInfo> getCardInfoArrayList() {return cardInfoArrayList;}
    public void setCardInfoArrayList(ArrayList<CardInfo> cardInfoArrayList) {this.cardInfoArrayList = cardInfoArrayList;}
    /**
     * TAG for debugging
     */
    public static final String TAG = "CustomerDatabase";
    /**
     * Singleton instance
     */
    private static CustomerDatabase database;
    /**
     * database name
     */
    public static String DATABASE_NAME = "customer.db";
    /**
     * table name
     */
    public static String TABLE_CUSTOMER_INFO = "CUSTOMER_INFO";
    public static String TABLE_CARD_INFO = "CARD_INFO";
    public static String TABLE_CARDS = "CARDS";
    public static String TABLE_SMS_DATA = "sms_data";
    /**
     * version
     */
    public static int DATABASE_VERSION = 1;
    /**
     * Helper class defined
     */
    private DatabaseHelper dbHelper;
    /**
     * Database object
     */
    private SQLiteDatabase db;
    private Context context;


    /**
     * Constructor
     */
    private CustomerDatabase(Context context) {
        this.context = context;
        this.user = new User();
    }
    //클래스 전역변수
    private final String rootFolderName = "/cardManager";
    //public static String root = null; //메모를 저장하는 폴더의 root dir
    private String initPath()
    {
        String root = null;
        String sdcard= Environment.getExternalStorageState();
        if( ! sdcard.equals(Environment.MEDIA_MOUNTED) ) {
            //SD카드 UNMOUNTED
            Log.d("mstag","sdcard unmounted");
            root = "" + Environment.getRootDirectory().getAbsolutePath() + rootFolderName; //내부저장소의 주소를 얻어옴
        } else {
            //SD카드 MOUNT
            Log.d("mstag","sdcard mounted");
            root = "" + Environment.getExternalStorageDirectory().getAbsolutePath() + rootFolderName; //외부저장소의 주소를 얻어옴
        }
        Log.d("mstag","root dir is => "+root);
        return root;
    }
    public static CustomerDatabase getInstance(Context context) {
        if (database == null) {
            database = new CustomerDatabase(context);
        }
        return database;
    }
    /**
     * open database
     *
     * @return
     */
    public boolean open() {
        println("opening database [" + DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }
    /**
     * close database
     */
    public void close() {
        println("closing database [" + DATABASE_NAME + "].");
        db.close();
        database = null;
    }
    /**
     * execute raw query using the input SQL
     * close the cursor after fetching any result
     *
     * @param SQL
     * @return
     */
    public Cursor rawQuery(String SQL) {
        println("\nexecuteQuery called.\n");

        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
            println("cursor count : " + c1.getCount());
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
        }

        return c1;
    }
    public boolean execSQL(String SQL) {
        println("\nexecute called.\n");

        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
            return false;
        }

        return true;
    }
    public boolean setMonthlyCostData(int month,ArrayList<String> al)
    {
        boolean flg = false;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Cursor cs = null;
        try{
            String sql = "select cardName , sum(cost),month,year from "+TABLE_SMS_DATA+" where month = '"+month+"' group by cardName,year,month ";
            cs = rawQuery(sql);
            Cards.idsArrList.clear();
            Map<String,HashMap> maptest = new HashMap<>();

            if(cs.moveToFirst()){
                while(cs.moveToNext()){
                    //cardInfoArrayList.add(new CardInfo(cs));
                    if(cs.getCount() > 0)
                    {
                        DecimalFormat fmt=new DecimalFormat("##,###");
                        String cost = String.valueOf(cs.getLong(1));
                        Log.d("shue", "body:" + cs.getString(0));
                        Log.d("shue", "body:" + cs.getString(3)+cs.getString(2));
                        Log.d("shue", "body:" + al.get(0));
                        Log.d("shue", "body:" + cs.getLong(1));
                        Log.d("shue", "body:" + al.get(1));
                        //Log.d("shue", "body:" + fmt.format(cost));

                        //String str = cs.getString(0) + month+al.get(0)+fmt.format(cost)+al.get(1);


                        Calendar cal = Calendar.getInstance();
                        String yyyy = String.valueOf(cal.get(Calendar.YEAR));
                        String MM = String.valueOf((cal.get(Calendar.MONTH) + 1));
                        String dd = String.valueOf(cal.get(Calendar.DATE));
                        String HH = String.valueOf(cal.get(Calendar.HOUR));
                        String mm = String.valueOf(cal.get(Calendar.MINUTE));
                        String ss = String.valueOf(cal.get(Calendar.SECOND));

                        String str = cs.getString(0) + month+al.get(0)+cost+al.get(1);
                        Cards.idsArrList.add(str);
                        String cardNameconvert = cs.getString(0).replace("[","(").replace("]",")");
                        CostData cdata = new CostData(yyyy+MM+dd+HH+mm+ss,cardNameconvert,cs.getString(3)+cs.getString(2),cost,cal.getTime().toString(),this.getUser().getEmail(),getUser().getName(),getUser().getPhone());
                        if(maptest.containsKey(cdata.getYearMonth()))
                        {
                            HashMap<String,CostData> submap = maptest.get(cdata.getYearMonth());
                            submap.put(cdata.getCardName(),cdata);
                        }
                        else
                        {
                            HashMap<String,CostData> submap = new HashMap<>();
                            submap.put(cdata.getCardName(),cdata);
                            maptest.put(cdata.getYearMonth(),submap);
                        }

                        //CostData cdata = new CostData(cs.getString(0),cs.getString(3)+cs.getString(2),cost);

                        flg = true;
                    }
                }
            }

            updateFBDB(maptest);
        }
        catch(Exception ex)
        {
            Log.e(TAG, "Exception in setTableCustomerInfo()", ex);
            flg = false;
        }
        finally {
            if(cs != null)
                cs.close();
        }


        return flg;
    }
    private DatabaseReference mDatabase;
    public void updateFBDB(Map<String,HashMap> maptest)
    {
        Log.d("kdml",this.getUser().getFireBase_ID());
        //mDatabase.child("cost").push().setValue(cd);
        //String cardNameconvert = cd.getCardName().replace("[","(").replace("]",")");


        if(getUser().getFireBase_ID().length() > 0)
            FirebaseDatabase.getInstance().getReference().child("costsmap").child(getUser().getFireBase_ID()).setValue(maptest);
    }

    public boolean setTableCustomerInfo()
    {
        boolean flg = false;
        if(user.getName() == null || user.getName().length() == 0)
        {
            Cursor cs = null;
            try{
                String sql = "select CUSTOMER_NAME,CUSTOMER_EMAIL,LAST_SMS_TIME from "+TABLE_CUSTOMER_INFO;
                cs = rawQuery(sql);
                if(cs.getCount() == 0)
                    insertGuestInfo();
                else
                {
                    cs.moveToNext();
                    user.setName(cs.getString(0));
                    user.setEmail(cs.getString(1));
                    setLastSMSTime(cs.getInt(2));
                    flg = true;
                }
            }
            catch(Exception ex)
            {
                Log.e(TAG, "Exception in setTableCustomerInfo()", ex);
                flg = false;
            }
            finally {
                if(cs != null)
                    cs.close();
            }
        }
        else if(user.getName() != null && user.getName().length() > 0)
        {
            flg = true;
        }

        return flg;
    }

    public void insertGuestInfo()
    {
        ContentValues values = new ContentValues();
        values.put("CUSTOMER_NAME","Guest");
        values.put("CUSTOMER_EMAIL","Guest Email");
        values.put("LAST_SMS_TIME",1);
        db.insert(TABLE_CUSTOMER_INFO,null,values);
        setTableCustomerInfo();
    }

    public boolean insertSMSData(ContentValues values)
    {
        try {
            db.insert(TABLE_SMS_DATA,null,values);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in insertSMSData()", ex);
            return false;
        }
        return true;
    }

    public boolean updateUserInfo_Email_FireBaseID(User user)
    {
        this.setUser(user);
        String UPDATE_SQL = "update " + TABLE_CUSTOMER_INFO +" set CUSTOMER_EMAIL = '" + user.getEmail() +"', CUSTOMER_NAME = '"+user.getName()  +"', FireBase_ID = '" + user.getFireBase_ID()+"'";
        try {
            db.execSQL(UPDATE_SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in UPDATE_SQL TABLE_CUSTOMER_INFO", ex);
            return false;
        }
        return true;
    }

    public boolean updateUserInfo(User user)
    {
        String UPDATE_SQL = "update " + TABLE_CUSTOMER_INFO +" set CUSTOMER_EMAIL = '" + user.getEmail() +"', CUSTOMER_NAME = '" + user.getName()+"'";
        try {
            db.execSQL(UPDATE_SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in UPDATE_SQL TABLE_CUSTOMER_INFO", ex);
            return false;
        }
        return true;
    }

    public boolean setCardInfo()
    {
        boolean flg = false;
        if(cardInfoArrayList == null || cardInfoArrayList.size() == 0)
        {
            cardInfoArrayList = new ArrayList<>();
            Cursor cs = null;
            try{
                String sql = "select CARD_COMPANY,CARD_TEL_NUM from "+TABLE_CARD_INFO+" order by CARD_TEL_NUM asc";
                cs = rawQuery(sql);
                if(cs.moveToFirst()){
                    while(cs.moveToNext()){
                        cardInfoArrayList.add(new CardInfo(cs));
                    }
                }

                flg = true;
            }
            catch(Exception ex)
            {
                Log.e(TAG, "Exception in setTableCustomerInfo()", ex);
                flg = false;
            }
            finally {
                cs.close();
            }
        }
        else if (cardInfoArrayList != null && cardInfoArrayList.size() > 0)
        {
            flg = true;
        }
        return flg;
    }

    public void onUpdateDatabase()
    {
        dbHelper.onUpgrade(db,1,2);
    }

    //// DatabaseHelper 클래스
    public class DatabaseHelper extends SQLiteOpenHelper
    {
        public DatabaseHelper(Context context)
        {
            super(context, initPath()+"/"+DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db)
        {
            // TABLE_CUSTOMER_INFO
            println("creating table [" + TABLE_CUSTOMER_INFO + "].");

            // drop existing table
            String DROP_SQL = "drop table if exists " + TABLE_CUSTOMER_INFO;
            try {
                db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL TABLE_CUSTOMER_INFO", ex);
            }

            // create table
            String CREATE_SQL = "create table " + TABLE_CUSTOMER_INFO + "("
                    + "  CUSTOMER_EMAIL TEXT  NOT NULL, "
                    + "  CUSTOMER_NAME TEXT, "
                    + "  CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "  LAST_SMS_TIME INTEGER, "
                    + "  SMS_Read_Date DOUBLE, "
                    + "  FireBase_ID TEXT "
                    + ")";
            try {
                db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL TABLE_CUSTOMER_INFO", ex);
            }


            // TABLE_CARDS
            println("creating table [" + TABLE_CARDS + "].");

            // drop existing table
            DROP_SQL = "drop table if exists " + TABLE_CARDS;
            try {
                db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL TABLE_CARDS", ex);
            }

            // create table
            CREATE_SQL = "create table " + TABLE_CARDS +"(  " +
                    "CARD_COMPANY TEXT," +
                    "CARD_NAME TEXT," +
                    "DATE_START TEXT," +
                    "DATE_END TEXT," +
                    "COST TEXT," +
                    "MANAGER_CODE TEXT," +
                    "MANAGER_NAME TEXT," +
                    "CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "SMS_RECEIVE_DATE TEXT PRIMARY KEY DESC NOT NULL UNIQUE)";
            try {
                db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL TABLE_CARDS", ex);
            }

            // TABLE_SMS_DATA
            println("creating table [" + TABLE_SMS_DATA + "].");

            // drop existing table
            DROP_SQL = "drop table if exists " + TABLE_SMS_DATA;
            try {
                db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL TABLE_SMS_DATA", ex);
            }

            // create table
            CREATE_SQL = "create table " + TABLE_SMS_DATA +"(  " +
                    "dataTime NUMBER NOT NULL ON CONFLICT IGNORE UNIQUE, " +
                    "dateTimeConvert NVARCHAR(20), " +
                    "text TEXT, " +
                    "cost NUMBER, " +
                    "type NVARCHAR(10), " +
                    "company NVARCHAR(10), " +
                    "month INT, " +
                    "year INT, " +
                    "day INT, " +
                    "cardName VARCHAR(20));";
            try {
                db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL TABLE_SMS_DATA", ex);
            }

            // TABLE_CARD_INFO
            println("creating table [" + TABLE_CARD_INFO + "].");

            // drop existing table
            DROP_SQL = "drop table if exists " + TABLE_CARD_INFO;
            try {
                db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL TABLE_CARD_INFO", ex);
            }

            // create table
            CREATE_SQL = "create table " + TABLE_CARD_INFO +"(  " +
                    "CARD_COMPANY TEXT,   " +
                    "CARD_NAME TEXT,   " +
                    "CARD_TEL_NUM TEXT  " +
                    "DATE_UPDATE TEXT,   " +
                    "PARSING_RULE TEXT,   " +
                    "CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP )";
            try {
                db.execSQL(CREATE_SQL);
                insertCardInfo();
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL TABLE_CARD_INFO", ex);
            }
        }

        public void insertCardInfo()
        {
              String[][] cardCompany={
                    {"현대","15776200"},
                    {"신한","15447200"},
                    {"삼성","15888900"},
                    {"KB국민","15881788"},
                    {"롯데","15888100"},
                    {"농협","15881600"},
                    {"하나","18001111"},
                    {"기업","15884000"},
                    {"우리","00000001"},
                    {"씨티","00000002"},
                    {"외환","00000003"},
                    {"SC(스탠다드)","00000004"}
            };
            try {
                for (int i = 0; i < cardCompany.length; i++) {
                    ContentValues values = new ContentValues();
                    values.put("CARD_COMPANY", cardCompany[i][0]);
                    values.put("CARD_TEL_NUM", cardCompany[i][1]);
                    db.insert(TABLE_CARD_INFO, null, values);
                }
            }catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL TABLE_CARD_INFO", ex);
            }

        }

        public void onOpen(SQLiteDatabase db)
        {
            println("opened database [" + DATABASE_NAME + "].");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");
            try {
                if (oldVersion < newVersion) {   // version 1
                    //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD_INFO);
                    //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
                    //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER_INFO);
                    onCreate(db);
                }
            }catch (Exception ex) {

            }
        }

    }

    private void println(String msg) {
        Log.d(TAG, msg);
    }


}
