package com.example.a402_24.day_03_register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    final static String LOG_TAG =  "KNKUserDoing";
    public static final String MyPREFERENCES = "MyPrefs";



    private HomeFragment homeFragment;
    private WriteFragment writeFragment;
    private UserFragment userFragment;

    private SearchFragment searchFragment;
    private ConfigureFragment configureFragment;
    private String member_gson;


    BottomNavigationView bottomNavView;
    TextView user_id;
    ImageView user_image;
    Button btn_logout;




    public void initRefs(){
        Gson gson = new Gson();

        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        member_gson = sharedPreferences.getString("info", null);
        final Member member = gson.fromJson(member_gson, Member.class);

        homeFragment = new HomeFragment();
        writeFragment = new WriteFragment();
        userFragment = new UserFragment();
        searchFragment = new SearchFragment();
        configureFragment = new ConfigureFragment();




        bottomNavView = (BottomNavigationView) findViewById(R.id.bottomNavView);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.search:

                        setFragment(searchFragment);

                        return true;
                    case R.id.hommy:

                        setFragment(homeFragment);

                        return true;

                    case R.id.upload:

                        setFragment(writeFragment);

                        return true;


                    case R.id.user:

                        setFragment(userFragment);

                        UserFragment userFragment = (UserFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentFrame);
                        return true;
                    case R.id.configure:

                        setFragment(configureFragment);

                        return true;
                }
                return false;
            }
        });


        /*
        try {
            if (member.getMember_id() != null) {
                output_id.setText(member.getMember_id());


                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Bitmap bitmap;

                            URL endPoint = new URL("http://192.168.0.19:8080");
                            String filePath = new String(member.getMember_profile_pic());
                            URL filePoint = new URL(endPoint.toString() + filePath);
                            Log.d(LOG_TAG, "filePoint" + filePoint);

                            HttpURLConnection conn = (HttpURLConnection) filePoint.openConnection();
                            conn.setDoInput(true);
                            conn.connect();

                            InputStream is = conn.getInputStream();

                            bitmap = BitmapFactory.decodeStream(is);

                            user_ImageView.setImageBitmap(bitmap);

                            is.close();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });


            } else {
                output_id.setText(null);

            }
        } catch(Exception e){
            e.printStackTrace();
        }
        */

    }
    /*
    public void setEvents(){
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), Before_Register.class);
                startActivity(intent);

            }
        });
    }
    */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initRefs();
        // setEvents();
        setFragment(homeFragment);

    }

    private void setFragment(Fragment fragment){
        Bundle bundle = new Bundle(1);
        bundle.putString("member", member_gson);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentFrame, fragment);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }


}
