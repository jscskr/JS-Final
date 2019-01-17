package com.example.a402_24.day_03_register;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class clickAlertUserPage extends AppCompatActivity {
    private static final String ip ="http://172.30.1.21:8080";
    public static final String MyPREFERENCES = "MyPrefs";
    ImageView member_profile;
    Button followBtn;
    Button watch_member_board;
    TextView member_id;
    TextView member_name;
    TextView member_gender;
    TextView member_birthday;
    TextView member_Zip_code;
    TextView member_Street_address;
    TextView member_detailed_address;



    public void setRefs(){
        member_profile = findViewById(R.id.imageView2);
        followBtn = findViewById(R.id.followBtn);
        watch_member_board = findViewById(R.id.watch_member_board);
        member_id = findViewById(R.id.textView15);
        member_name = findViewById(R.id.textView16);
        member_gender = findViewById(R.id.textView17);
        member_birthday = findViewById(R.id.textView19);
        member_Zip_code = findViewById(R.id.textView18);
        member_Street_address = findViewById(R.id.textView20);
        member_detailed_address = findViewById(R.id.textView21);


        final Intent intent = getIntent();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
try{
    URL url = new URL(ip+intent.getStringExtra("member_profile_pic"));
    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
    httpURLConnection.setDoInput(true);
    InputStream is = httpURLConnection.getInputStream();
    Bitmap bitmap = BitmapFactory.decodeStream(is);
    member_profile .setImageBitmap(bitmap);
}catch (Exception e){

}


            }
        });

        member_id.setText("ID:"+intent.getStringExtra("member_id"));
        member_name.setText("이름:"+intent.getStringExtra("member_name"));
        member_gender.setText("성별:"+intent.getStringExtra("member_gender"));
        member_birthday.setText("생일:"+intent.getStringExtra("member_birthday"));
        member_Zip_code.setText("우편번호:"+intent.getStringExtra("member_Zip_code"));
        member_Street_address.setText("주소:"+intent.getStringExtra("member_Street_address"));
        member_detailed_address.setText("상세주소:"+intent.getStringExtra("member_detailed_address"));

    }

    public void setEvents(){
        final Intent intent1 = getIntent();
        final Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String member_gson = sharedPreferences.getString("info", null);
        final Member member = gson.fromJson(member_gson, Member.class);
        watch_member_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),memberWroteBoard.class);
                intent.putExtra("write_member_id",intent1.getStringExtra("member_id"));
                startActivity(intent);
            }
        });

        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String friend_id1 = "friend_id1="+member.getMember_id();
                final String friend_id1_profile_pic = "&friend_id1_profile_pic="+member.getMember_profile_pic();
                final String friend_id1_name = "&friend_id1_name="+member.getMember_name();
                final String friend_id2 = "&friend_id2="+intent1.getStringExtra("member_id");
                final String friend_id2_profile_pic = "&friend_id2_profile_pic="+intent1.getStringExtra("member_profile_pic");
                final String friend_id2_name = "&friend_id2_name="+intent1.getStringExtra("member_name");

Log.d("mem",friend_id1);
                Log.d("mem",friend_id2);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url1 = new URL(ip + "/JS/android/insertFriend");
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.getOutputStream().write(friend_id1.getBytes());
                            httpURLConnection.getOutputStream().write(friend_id1_profile_pic.getBytes());
                            httpURLConnection.getOutputStream().write(friend_id1_name.getBytes());
                            httpURLConnection.getOutputStream().write(friend_id2.getBytes());
                            httpURLConnection.getOutputStream().write(friend_id2_profile_pic.getBytes());
                            httpURLConnection.getOutputStream().write(friend_id2_name.getBytes());

                            if(httpURLConnection.getResponseCode()==200){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"친구추가성공",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        } catch (Exception e) {

                        }
                    }
                });
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_alert_user_page);
setRefs();
setEvents();
    }
}
