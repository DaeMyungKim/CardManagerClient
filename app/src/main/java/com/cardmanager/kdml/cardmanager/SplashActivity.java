package com.cardmanager.kdml.cardmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by kdml on 2016-05-24.
 */
public class SplashActivity extends Activity{
    public static final int REQUEST_CODE_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            CustomerDatabase cd = CustomerDatabase.getInstance(this);
            // DB 확인.
            if(!cd.open())
            {
                // db 열기 실패, 종료
                this.finish();
            }
            // db 수정되면 업데이트
            cd.onUpdateDatabase();

            //네트워크 접속 여부
            if(true)
            {
                // 로그인 세션 유무
                if(true)
                {
                    //
                }
                else
                {
                    // 로그인 사용하지 않거나 없는 경우
                }
            }
            else
            {

            }
            Intent intent = new Intent(getBaseContext(),EmailPasswordActivity.class);
            Bundle bnd = new Bundle();
            bnd.putInt("loginFlag",1);
            intent.putExtras(bnd);
            startActivityForResult(intent,REQUEST_CODE_LOGIN);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent Data){
        super.onActivityResult(requestCode,resultCode,Data);

        switch (requestCode)
        {
            case REQUEST_CODE_LOGIN:
                if(resultCode == RESULT_OK)
                {
                    int i = Data.getExtras().getInt("data");
                    if(i == 1)
                    {
                        //Toast.makeText(getBaseContext(),getResources().getText(R.string.main_message1),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(this,MainActivity.class));
                        finish();
                    }
                }

                break;
        }
    }
}
