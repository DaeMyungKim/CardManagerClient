package com.cardmanager.kdml.cardmanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CardAddFormActivity extends AppCompatActivity {

    Spinner s;
    Spinner s2;
    TextView tv1;
    TextView tv2;
    DateFormat fmDate = DateFormat.getDateInstance();
    Calendar datestart = Calendar.getInstance();
    Calendar dateend = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_add_form);
        CustomerDatabase cd = CustomerDatabase.getInstance(this);
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

        s = (Spinner)findViewById(R.id.spnCompany);
        ArrayAdapter adt = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,cardCompany[0]);
        //adt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        s.setAdapter(adt);
        s.setOnItemSelectedListener(onItemSelected_listener);

        tv1 = (TextView)findViewById(R.id.editTextStardDate);
        tv1.setOnClickListener(onStartTextViewClicked_listener);
        tv2 = (TextView)findViewById(R.id.editTextEndDate);
        tv2.setOnClickListener(onEndTextViewClicked_listener);
    }

    private TextView.OnClickListener onStartTextViewClicked_listener = new TextView.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(CardAddFormActivity.this,sd,datestart.get(Calendar.YEAR),datestart.get(Calendar.MONTH),datestart.get(Calendar.DAY_OF_MONTH)).show();
        }
    };

    private DatePickerDialog.OnDateSetListener sd = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            datestart.set(Calendar.YEAR,year);
            datestart.set(Calendar.MONTH,monthOfYear);
            datestart.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateStartLabel();
        }
    };
    private TextView.OnClickListener onEndTextViewClicked_listener = new TextView.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(CardAddFormActivity.this,ed,dateend.get(Calendar.YEAR),dateend.get(Calendar.MONTH),dateend.get(Calendar.DAY_OF_MONTH)).show();
        }
    };

    private DatePickerDialog.OnDateSetListener ed = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateend.set(Calendar.YEAR,year);
            dateend.set(Calendar.MONTH,monthOfYear);
            dateend.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateEndLabel();
        }
    };
    private Spinner.OnItemSelectedListener onItemSelected_listener = new Spinner.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            setCardList();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void setCardList()
    {
        CustomerDatabase cd = CustomerDatabase.getInstance(this);
        ArrayList<String> arr = new ArrayList<>();// cd.getCardInfo(s.getSelectedItem().toString()).getCard_List();
        s2 = (Spinner)findViewById(R.id.spnCard);
        ArrayAdapter adt = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arr);
        //adt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        s2.setAdapter(adt);
    }
    private void updateStartLabel()
    {
        tv1.setText(fmDate.format(datestart.getTime()));
    }
    private void updateEndLabel()
    {
        tv2.setText(fmDate.format(dateend.getTime()));
    }
    public void onClickOK(View v)
    {
        this.finish();
    }

    public void onClickCancel(View v) {
        this.finish();
    }


}
