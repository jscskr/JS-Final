package com.example.a402_24.day_03_register;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Rv_board_result extends AppCompatActivity {

    Bitmap bitmap;
    TextView Rv_board_resultText;
    TextView Rv_board_resultImg;
    Button goBoard;

    public void setRefs(){
        Rv_board_resultText = findViewById(R.id.textView5);
        Rv_board_resultImg = findViewById(R.id.Rv_board_resultImg);
        goBoard = findViewById(R.id.button2);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        switch (result){
            case "{\"msg\":\"success\"}":
                Rv_board_resultText.setText("게시글 작성 성공");
                Rv_board_resultImg.setBackgroundResource(R.drawable.ic_sentiment_very_satisfied_black_24dp);
                break;
            case "{\"msg\":\"fail\"}":
                Rv_board_resultText.setText("게시글 작성 실패");
                Rv_board_resultImg.setBackgroundResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp);
                break;
        }
    }

    public void setEvents(){
        goBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                Intent intent = new Intent(getApplicationContext(),UserActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_board_result);
        setRefs();
        setEvents();
    }
}
