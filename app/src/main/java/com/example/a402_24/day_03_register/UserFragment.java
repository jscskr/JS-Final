package com.example.a402_24.day_03_register;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    private static final String ip ="http://172.30.1.21:8080";
    final static String LOG_TAG =  "KNKUserDoing";
    public static final String MyPREFERENCES = "MyPrefs";



    ImageView member_profile;
    Button followBtn;
    Button watch_member_board;
    Button followAccept;
    TextView member_id;
    TextView member_name;
    TextView member_gender;
    TextView member_birthday;
    TextView member_register_date;
    TextView member_Zip_code;
    TextView member_Street_address;
    TextView member_detailed_address;
    Button button;
Gson gson = new Gson();
    public void setRefs(View v){
        member_profile = v.findViewById(R.id.imageView2);
        followBtn = v.findViewById(R.id.followBtn);
        followAccept = v.findViewById(R.id.followAccept);
        watch_member_board = v.findViewById(R.id.watch_member_board);
        member_id = v.findViewById(R.id.textView15);
        member_name = v.findViewById(R.id.textView16);
        member_gender = v.findViewById(R.id.textView17);
        member_birthday = v.findViewById(R.id.textView19);
        member_register_date = v.findViewById(R.id.textView22);
        member_Zip_code = v.findViewById(R.id.textView18);
        member_Street_address = v.findViewById(R.id.textView20);
        member_detailed_address = v.findViewById(R.id.textView21);
        button= v.findViewById(R.id.button);


        String member_Str = getArguments().getString("member");
        final Member member = gson.fromJson(member_Str, Member.class);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(ip+member.getMember_profile_pic());
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setDoInput(true);
                    InputStream is = httpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    member_profile .setImageBitmap(bitmap);
                }catch (Exception e){

                }


            }
        });
        member_id.setText("ID:"+member.getMember_id());
        member_name.setText("이름:"+member.getMember_name());
        member_gender.setText("성별:"+member.getMember_gender());
        member_birthday.setText("생일:"+member.getMember_birthday());
        member_Zip_code.setText("우편번호:"+member.getMember_Zip_code());
        member_Street_address.setText("주소:"+member.getMember_Street_name_address());
        member_detailed_address.setText("상세주소:"+member.getMember_Detailed_Address());


    }

    public void setEvent(View v) {
        Gson gson = new Gson();
        String member_Str = getArguments().getString("member");
        final Member member = gson.fromJson(member_Str, Member.class);
watch_member_board.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity().getApplicationContext(),memberWroteBoard.class);
        intent.putExtra("write_member_id",member.getMember_id());
        startActivity(intent);
    }
});


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(getActivity().getApplicationContext(), Before_Register.class);
                startActivity(intent);
            }
        });
        followAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),memberFriendAccept.class);
                startActivity(intent);
            }
        });
    }

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View v =  inflater.inflate(R.layout.fragment_user, container, false);
        setRefs(v);
         setEvent(v);
         return v;
    }

}
