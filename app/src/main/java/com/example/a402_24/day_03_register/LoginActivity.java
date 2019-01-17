package com.example.a402_24.day_03_register;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.MaskFilter;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String ip ="http://172.30.1.21:8080";
    final static String LOG_TAG =  "KNKLoginDoing";
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;

    Toolbar toolbar;
    EditText input_login_id;
    EditText input_login_pw;
    Button btn_login_submit;


    public void initRefs(){
        input_login_id = (EditText) findViewById(R.id.input_login_id);
        input_login_pw = (EditText) findViewById(R.id.input_login_pw);
        btn_login_submit = (Button) findViewById(R.id.btn_login_submit);
    }

    public void setEvents(){
        btn_login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            URL endPoint = new URL(ip+"/JS/android/login");
                            HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();

                            String id = input_login_id.getText().toString();
                            String password = input_login_pw.getText().toString();

                            String requestParam = String.format("id=%s&password=%s", id, password);

                            myConnection.setDoOutput(true);
                            myConnection.getOutputStream().write(requestParam.getBytes());

                            myConnection.setRequestMethod("POST");
                            Log.d(LOG_TAG, "1까지 실행되었습니다.");

                            Log.d(LOG_TAG, "2까지 실행되었습니다.");

                            if(myConnection.getResponseCode()==200) {
                                BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "UTF-8"));
                                StringBuffer buffer = new StringBuffer();
                                String temp = null;
                                while((temp = in.readLine()) != null){
                                    buffer.append(temp);
                                }
                                String decode = URLDecoder.decode(buffer.toString(), "UTF-8");
                                Gson gson = new Gson();
                                Member member = gson.fromJson(decode, Member.class);
                                Log.d(LOG_TAG,decode);

                                boolean check = member.getMember_password().toString().equals(input_login_pw.getText().toString());

                                if(check){
                                    sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    editor.putString("info", decode);
                                    editor.apply();
                                    /*
                                    String cookieString =
                                            android.webkit.CookieManager.getInstance().getCookie("http://192.168.10.208:8080/JS/android/login");
                                    if(cookieString != null){
                                        myConnection.setRequestProperty("Cookie", cookieString);
                                    }

                                    Map<String, List<String>> headerFields = myConnection.getHeaderFields();
                                    String COOKIE_HEADER = "Set-Cookie";
                                    List<String> cookiesHeader = headerFields.get(COOKIE_HEADER);

                                    if(cookiesHeader != null){
                                        for(String cookie : cookiesHeader){
                                            String cookieName = HttpCookie.parse(cookie).get(0).getName();
                                            String cookieValue = HttpCookie.parse(cookie).get(0).getValue();

                                            android.webkit.CookieManager.getInstance().setCookie("http://192.168.10.208:8080/JS/android/login", cookieString);
                                        }

                                    }
                                    */
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplication(), "로그인 되었습니다", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Intent intent = new Intent(getApplication(), UserActivity.class);

                                    startActivity(intent);
                                    finish();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplication(), "로그인이 실패하였습니다", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                        } catch(Exception e){
                            Log.d(LOG_TAG, e.toString());
                        }
                    }
                });

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        initRefs();
        setEvents();
    }
}
