package com.example.a402_24.day_03_register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Message_result extends AppCompatActivity {
    TextView Message_resultText;
    TextView Message_resultImg;
    Button goHome;

    public void setRefs(){
        Message_resultText = findViewById(R.id.textView5);
        Message_resultImg = findViewById(R.id.Message_resultImg);
        goHome = findViewById(R.id.button2);
    }

    public void setEvents(){
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MessageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_result);
        setRefs();
        setEvents();


        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        switch (result){
            case "{\"msg\":\"success\"}":
                Message_resultText.setText("메시지 전송 성공");
                Message_resultImg.setBackgroundResource(R.drawable.ic_sentiment_very_satisfied_black_24dp);
                break;
            case "{\"msg\":\"fail\"}":
                Message_resultText.setText("메시지 전송 실패");
                Message_resultImg.setBackgroundResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp);
                break;
        }
    }
}
