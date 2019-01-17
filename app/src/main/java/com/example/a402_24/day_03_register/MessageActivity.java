package com.example.a402_24.day_03_register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    private static final String ip ="http://172.30.1.21:8080";
    public static final String MyPREFERENCES = "MyPrefs";
    String member_id;
    ArrayList<Message> messageArrayList;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
TextView sendMessageStore;
    TextView writeMessage;



    public void setRefs(){

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        sendMessageStore = findViewById(R.id.sendMessageStore);
        writeMessage = findViewById(R.id.writeMessage);

        // 리사이클뷰의 각 영역 클릭시 클릭이벤트 처리
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener(){

            //
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent intent = new Intent(getApplicationContext(),MessageReceiveSelected.class);
                intent.putExtra("message_id",messageArrayList.get(position).getMessage_id());
                intent.putExtra("message_title",messageArrayList.get(position).getMessage_title());
                intent.putExtra("message_content",messageArrayList.get(position).getMessage_content());
                intent.putExtra("message_picture",messageArrayList.get(position).getMessage_picture());
                intent.putExtra("message_WriteMember_id",messageArrayList.get(position).getMember_id());

                Log.d("message_id",Integer.toString(messageArrayList.get(position).getMessage_id()));
                startActivity(intent);
            }
        });

        // 열기 다이얼로그 창 생성할 예정
        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(MessageActivity.this, "길게 눌렀구나 " + messageArrayList.get(position).getMember_id(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        writeMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Write_message.class);
                startActivity(intent);
            }
        });

        sendMessageStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),MessageSendStore.class);
                startActivity(intent);

            }
        });


    }





    public void setConnection(){
        String member_gson;
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        member_gson = sharedPreferences.getString("info", null);
        final Member member = gson.fromJson(member_gson, Member.class);

        final String member_id = "member_id="+member.getMember_id();


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(ip+"/JS/android/receiveMessageStore");
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
                        String JsonMessage = sb.toString();
                        JSONArray jsonArray = new JSONArray(JsonMessage);
                        messageArrayList = new ArrayList<>();
                        for(int i = 0 ; i < jsonArray.length() ; i++){
                            Message message = new Message();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //메시지 번호
                            message.setMessage_id(jsonObject.getInt("message_id"));
// 보낸사람
                            message.setMember_id(jsonObject.getString("member_id"));
// 나
                            message.setMember_receiver(jsonObject.getString("member_receiver"));
                            message.setMessage_title(jsonObject.getString("message_title"));
                            message.setMessage_content(jsonObject.getString("message_content"));
// 메시지 이미지 파일 첨부
                            // 메시지 이미지 파일 첨부하지않는 경우도 있으므로
                            if(jsonObject.has("message_picture")) {
                                message.setMessage_picture(jsonObject.getString("message_picture"));
                            }
                            // 회원가입 시 프로필 사진 지정하지 않은사람도 있으므로
                            if(jsonObject.has("message_profil_pic")) {
                                message.setMessage_profil_pic(jsonObject.getString("message_profil_pic"));
                            }
                            message.setMessage_send_date(jsonObject.getString("message_send_date"));

                            messageArrayList.add(message);
                            Log.d("messageArrayList:",messageArrayList.toString());

                        }
                        // 리사이클뷰 안에 cardview 에 넣어줄 값 설정
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<Message> messageInfoArrayList = new ArrayList<>();
                                if(messageArrayList != null) {
                                    Log.d("inRunOn:",messageArrayList.toString());
                                    for (int i = 0; i < messageArrayList.size(); i++) {
                                        Log.d("messageId:",messageArrayList.get(i).getMember_id());
                                        Log.d("messageprofile:",messageArrayList.get(i).getMessage_profil_pic());
                                        messageInfoArrayList.add(new Message(messageArrayList.get(i).getMember_id(),messageArrayList.get(i).getMessage_title(),messageArrayList.get(i).getMessage_profil_pic(),messageArrayList.get(i).getMessage_send_date()));
                                    }

                                    RecyclerAdapter_message myAdapter = new RecyclerAdapter_message(messageInfoArrayList);

                                    mRecyclerView.setAdapter(myAdapter);
                                }
                            }
                        });

                    }
                }catch(Exception e){

                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setRefs();

        setConnection();






    }
}
