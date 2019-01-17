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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class memberWroteBoard extends AppCompatActivity {
    private static final String ip ="http://172.30.1.21:8080";
    public static final String MyPREFERENCES = "MyPrefs";
    RequestManager mGlideRequestManager;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Rv_board> Rv_boardArrayList;

    RecyclerAdapter_board myAdapter;
    public void setRefs(){
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        if (mRecyclerView.getLayoutManager() != mLayoutManager) {
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
    }

    public void setEvents(){
        // 리사이클뷰의 각 영역 클릭시 클릭이벤트 처리
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener(){
            //
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent intent = new Intent(getApplicationContext(),Rv_boardSelected.class);
                intent.putExtra("rv_board_index",Rv_boardArrayList.get(position).getRv_board_index());
                intent.putExtra("rv_board_title",Rv_boardArrayList.get(position).getRv_board_title());
                intent.putExtra("rv_board_content",Rv_boardArrayList.get(position).getRv_board_content());
                intent.putExtra("rv_board_picture",Rv_boardArrayList.get(position).getRv_board_picture());
                // 선택된 게시글이 현재 로그인한 유저가 작성한것인지 확인하기위해서
                intent.putExtra("rv_board_WriteMember_id",Rv_boardArrayList.get(position).getMember_id());
                startActivity(intent);
            }
        });
    }
    public void setBoard(){
Intent intent = getIntent();
        final String member_id = intent.getStringExtra("write_member_id");
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                   String member_id1 = "member_id="+member_id;

                    URL url = new URL(ip+"/JS/android/rv_board/memberWrote");
                    HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
                    httpUrlConnection.setRequestMethod("POST");
                    httpUrlConnection.setDoOutput(true);
                    httpUrlConnection.setDoInput(true);
                    httpUrlConnection.getOutputStream().write(member_id1.getBytes());

                    if (httpUrlConnection.getResponseCode() == 200) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                        StringBuffer sb = new StringBuffer();
                        String temp = null;
                        while((temp = br.readLine())!=null){
                            sb.append(temp);
                        }
                        String JsonMember = sb.toString();
                        JSONArray jsonArray = new JSONArray(JsonMember);
                        final ArrayList<Rv_board> Rv_board_List = new ArrayList<>();
                        for( int i = 0 ; i < jsonArray.length() ; i++){
                            Rv_board rv_board = new Rv_board();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            rv_board.setRv_board_index(jsonObject.getInt("rv_board_index"));
                            rv_board.setRv_board_post_date(jsonObject.getString("rv_board_post_date"));
                            rv_board.setMember_id(jsonObject.getString("member_id"));
                            rv_board.setRv_board_title(jsonObject.getString("rv_board_title"));
                            rv_board.setRv_board_content(jsonObject.getString("rv_board_content"));
                            rv_board.setRv_board_location(jsonObject.getString("rv_board_location"));
                            if(jsonObject.has("rv_board_picture")) {
                                rv_board.setRv_board_picture(jsonObject.getString("rv_board_picture"));
                            }
                            rv_board.setMember_profile(jsonObject.getString("member_profile"));
                            rv_board.setRv_board_comments(jsonObject.getInt("rv_board_comments"));
                            rv_board.setRv_board_recommend(jsonObject.getInt("rv_board_recommend"));
                            rv_board.setRv_board_heart(jsonObject.getInt("rv_board_heart"));
                            rv_board.setRv_board_count(jsonObject.getInt("rv_board_count"));
                            rv_board.setLoginUser(member_id1);

                            Rv_board_List.add(rv_board);
                        }

                        mGlideRequestManager = Glide.with(getApplicationContext());

                        Rv_boardArrayList = new ArrayList<>();
                        if(Rv_board_List != null) {
                            Log.d("inRunOn:",Rv_board_List.get(0).getMember_id().toString());
                            for (int i = 0; i < Rv_board_List.size(); i++) {

                                Rv_boardArrayList.add(new Rv_board(Rv_board_List.get(i).getRv_board_index(),Rv_board_List.get(i).getRv_board_post_date(),Rv_board_List.get(i).getMember_id(),Rv_board_List.get(i).getRv_board_title(),Rv_board_List.get(i).getRv_board_content(),Rv_board_List.get(i).getRv_board_location(),Rv_board_List.get(i).getRv_board_picture(),Rv_board_List.get(i).getMember_profile(),Rv_board_List.get(i).getRv_board_comments(),Rv_board_List.get(i).getRv_board_recommend(),Rv_board_List.get(i).getRv_board_heart(),Rv_board_List.get(i).getRv_board_count(),Rv_board_List.get(i).getLoginUser()));
                                Log.d("inRunOn2:",Rv_boardArrayList.get(0).getMember_id());
                            }

                            myAdapter = new RecyclerAdapter_board(Rv_boardArrayList,mGlideRequestManager);

                            mRecyclerView.setAdapter(myAdapter);

                        }


                    }
                }catch (Exception e){

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_wrote_board);
        setRefs();
        setBoard();
        setEvents();
    }
}
