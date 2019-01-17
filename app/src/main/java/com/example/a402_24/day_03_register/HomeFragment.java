package com.example.a402_24.day_03_register;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String ip ="http://172.30.1.21:8080";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Rv_board> Rv_boardArrayList;
    RequestManager mGlideRequestManager;

    RecyclerAdapter_board myAdapter;
    SwipeRefreshLayout swipeRefreshLayout ;

    public void setRefs(View v){
        mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(v.getContext().getApplicationContext());
        if (mRecyclerView.getLayoutManager() != mLayoutManager) {
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
       swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
    }

    public void setEvents(View v){
        // 리사이클뷰의 각 영역 클릭시 클릭이벤트 처리
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener(){
            //
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent intent = new Intent(getActivity(),Rv_boardSelected.class);
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        setRefs(v);
        setEvents(v);
        setBoard(v);
        setRefresh();
        mGlideRequestManager = Glide.with(this);
        return v;
    }

    public void setRefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()

        {
            public void onRefresh () {
                // 게시글 새로고침 해주기
                myAdapter.notifyDataSetChanged();
                // 이거 선언해줘야지 새로고침 아이콘 사라진다
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }



    public void setBoard(final View v){

        Log.d("실행asd","실행");
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    Gson gson = new Gson();
                    String member_Str = getArguments().getString("member");
                    final Member member = gson.fromJson(member_Str, Member.class);

                    URL url = new URL(ip+"/JS/android/rv_board/All");
                    HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
                    httpUrlConnection.setRequestMethod("POST");
                    httpUrlConnection.setDoInput(true);

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
                            rv_board.setLoginUser(member.getMember_id());

                            Rv_board_List.add(rv_board);
                        }



                        Rv_boardArrayList = new ArrayList<>();
                        if(Rv_board_List != null) {

                            for (int i = 0; i < Rv_board_List.size(); i++) {

                                Rv_boardArrayList.add(new Rv_board(Rv_board_List.get(i).getRv_board_index(),Rv_board_List.get(i).getRv_board_post_date(),Rv_board_List.get(i).getMember_id(),Rv_board_List.get(i).getRv_board_title(),Rv_board_List.get(i).getRv_board_content(),Rv_board_List.get(i).getRv_board_location(),Rv_board_List.get(i).getRv_board_picture(),Rv_board_List.get(i).getMember_profile(),Rv_board_List.get(i).getRv_board_comments(),Rv_board_List.get(i).getRv_board_recommend(),Rv_board_List.get(i).getRv_board_heart(),Rv_board_List.get(i).getRv_board_count(),Rv_board_List.get(i).getLoginUser()));

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

}
