package com.cardmanager.kdml.cardmanager;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistUserActivity extends AppCompatActivity {
    Bundle extra;
    Intent intent;

    private Button btn;
    private EditText edt1;
    private EditText edt2;
    private EditText edt3;
    private TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_user);
        extra = new Bundle();
        intent = new Intent();
        btn = (Button)findViewById(R.id.btnRegist);
        edt1 = (EditText)findViewById(R.id.editText);
        edt2 = (EditText)findViewById(R.id.editText2);
        edt3 = (EditText)findViewById(R.id.editText5);
        view = (TextView)findViewById(R.id.textView5);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(edt1.getText().toString().length() == 0 || edt2.getText().toString().length() == 0 || edt3.getText().toString().length() == 0 )
                {
                    view.setText(getResources().getText(R.string.regist_message1));
                    return;
                }
                if(edt2.getText().toString().length() != 0 && edt3.getText().toString().length() != 0 && !edt2.getText().toString().equals(edt3.getText().toString()) )
                {
                    view.setText(getResources().getText(R.string.regist_message2));
                    return;
                }
                signUpUser();
            }
        });
    }

    private void signUpUser ()
    {
        String email = edt1.getText().toString();
        String password = edt2.getText().toString();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                FirebaseUser user = task.getResult().getUser();
                User userModel = new User(user.getEmail());
                databaseReference.child("users").child(user.getUid()).setValue(userModel);
                CustomerDatabase cd = CustomerDatabase.getInstance(this);
                cd.getUser().setEmail(email);
                cd.getUser().setName(email);
                cd.updateUserInfo(userModel);
                extra.putInt("data",1);
                intent.putExtras(extra);
                setResult(RESULT_OK,intent);

                finish();
            }
        }).addOnFailureListener( e ->{
            Log.d("mstag",e.toString());
            view.setText(e.toString());
        });
    }

}
