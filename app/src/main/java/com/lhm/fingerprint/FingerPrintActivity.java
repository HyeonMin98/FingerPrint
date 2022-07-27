package com.lhm.fingerprint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ajalt.reprint.core.AuthenticationFailureReason;
import com.github.ajalt.reprint.core.AuthenticationListener;
import com.github.ajalt.reprint.core.Reprint;

public class FingerPrintActivity extends AppCompatActivity {

    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        text = findViewById(R.id.text);

        //지문 사용을 위한 초기화(핸드폰 상태 확인)
        Reprint.initialize(FingerPrintActivity.this);

        if (checkSpec()){
            //지문인식 가능한 경우
            Reprint.authenticate(new AuthenticationListener() {
                @Override
                public void onSuccess(int moduleTag) {
                    //화면전환까지 해야한다.
                    text.setText("인증 성공");
                }

                @Override
                public void onFailure(AuthenticationFailureReason failureReason, boolean fatal, CharSequence errorMessage, int moduleTag, int errorCode) {
                    text.setText("인증실패, 다시 시도하세요.");
                }
            });
        }


    }   //onCreate();

    //지문인식이 가능한 기기인지 판단
    public boolean checkSpec(){
        //참이면 지문인식 센서가 존재한다.
        boolean hardware = Reprint.isHardwarePresent();

        //센서가 있어도 지문등록이 안되있으면 사용할수 없기때문에 확인을한다.
        boolean register = Reprint.hasFingerprintRegistered();

        if(!hardware){
            Toast.makeText(FingerPrintActivity.this, "지문인식 불가능 기기입니다.", Toast.LENGTH_SHORT).show();
        }else{
            if(!register){
                Toast.makeText(FingerPrintActivity.this, "등록된 지문이 없습니다.\n 지문등록을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
        return hardware && register;
    }



}