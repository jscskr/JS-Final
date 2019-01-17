package com.example.a402_24.day_03_register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class memberFriendAccept extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String ip ="http://172.30.1.21:8080";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Member> memberArrayList;


    public void setRefs(){
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


    }
/*
    public void setList(){
        final Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String member_gson = sharedPreferences.getString("info", null);
        final Member member = gson.fromJson(member_gson, Member.class);

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                           String member_id = "friend_id2="+member.getMember_id();

                            URL url = new URL(ip+"/JS/android/waitFriendList");
                            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
                            httpUrlConnection.setRequestMethod("POST");
                            httpUrlConnection.setDoOutput(true);
                            httpUrlConnection.setDoInput(true);
                            httpUrlConnection.getOutputStream().write(member_id.getBytes());

                            if (httpUrlConnection.getResponseCode() == 200) {
                                BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                                StringBuffer sb = new StringBuffer();
                                String temp = null;
                                while((temp = br.readLine())!=null){
                                    sb.append(temp);
                                }
                                String JsonMember = sb.toString();
                                Log.d("aaa",JsonMember);
                                JSONArray jsonArray = new JSONArray(JsonMember);
                                ArrayList<Member> memberList = new ArrayList<>();
                                for( int i = 0 ; i < jsonArray.length() ; i++){
                                    Member member = new Member();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    member.setMember_id(jsonObject.getString("friend_id1"));
                                    member.setMember_name(jsonObject.getString("friend_id1_name"));
                                    member.setMember_profile_pic(jsonObject.getString("friend_id1_profile_pic"));


                                    memberList.add(member);
                                }
                                Log.d("inRunOn123:",memberList.get(0).getMember_id());

                                memberArrayList = new ArrayList<>();

                                    Log.d("inRunOn:",memberList.get(0).getMember_gender().toString());
                                    for (int i = 0; i < memberList.size(); i++) {

                                        memberArrayList.add(new Member(memberList.get(i).getMember_id(),memberList.get(i).getMember_name(),memberList.get(i).getMember_profile_pic()));
                                        Log.d("inRunOn2:",memberArrayList.get(0).getMember_id());
                                    }

                                    RecyclerAdapter_report myAdapter = new RecyclerAdapter_report(memberArrayList);

                                    mRecyclerView.setAdapter(myAdapter);


                                    Log.d("inRunOn:","null");





                            }
                        }catch (Exception e){

                        }
                    }
                });


        // 리사이클뷰의 각 영역 클릭시 클릭이벤트 처리
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener(){
            //
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent intent = new Intent(getApplicationContext(),clickAlertUserPage.class);
                intent.putExtra("member_id",memberArrayList.get(position).getMember_id());
                intent.putExtra("member_name",memberArrayList.get(position).getMember_name());

                intent.putExtra("member_profile_pic",memberArrayList.get(position).getMember_profile_pic());


                startActivity(intent);
            }
        });
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_friend_accept);

        setRefs();

    }
}
