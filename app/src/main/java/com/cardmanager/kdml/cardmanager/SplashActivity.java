package com.cardmanager.kdml.cardmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by kdml on 2016-05-24.
 */
public class SplashActivity extends Activity{


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
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
