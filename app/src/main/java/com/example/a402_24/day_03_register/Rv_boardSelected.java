package com.example.a402_24.day_03_register;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Rv_boardSelected extends AppCompatActivity {
    private static final String ip ="http://172.30.1.21:8080";
    TextView rv_board_write_date;
    TextView rv_board_location;
    TextView rv_board_delete;

    LinearLayout memberInfo;

    TextView rv_board_report;
    RadioGroup radioGroup;
    String rv_board_report_result;
    RadioButton radioButton;
    RadioButton radioButton2;
    RadioButton radioButton3;

    ImageView write_member_profile;
    TextView write_member_id;

    TextView rv_board_watch;
    TextView rv_board_title;
    ImageView rv_board_image;

    TextView rv_board_comments;

    TextView rv_board_shared;

    LinearLayout rv_board_heart;
    ImageView rv_board_heart_img;
    TextView rv_board_heart_text;
    EditText rv_board_alert;
    Button rv_board_alertBtn;
    RecyclerView recyclerView;
    Member member1;
    public static final String MyPREFERENCES = "MyPrefs";

    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Alert> alertArrayList;
    public void setRefs() {

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String member_gson = sharedPreferences.getString("info", null);
        final Member member = gson.fromJson(member_gson, Member.class);
        final Intent intent = getIntent();

        // EditText 클릭 시 UI 강제로 밀어올려 키보드 input 에 위치 일치하도록
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        rv_board_write_date = findViewById(R.id.rv_board_write_date);
        rv_board_location = findViewById(R.id.rv_board_location);
        rv_board_delete = findViewById(R.id.rv_board_delete);
        rv_board_report = findViewById(R.id.rv_board_report);
        write_member_profile = findViewById(R.id.write_member_profile);
        write_member_id = findViewById(R.id.write_member_id);

        memberInfo = findViewById(R.id.memberInfo);

        rv_board_watch = findViewById(R.id.rv_board_watch);
        rv_board_title = findViewById(R.id.rv_board_title);
        rv_board_image = findViewById(R.id.rv_board_image);

        rv_board_comments = findViewById(R.id.rv_board_comments);

        rv_board_shared = findViewById(R.id.rv_board_shared);

        rv_board_heart = findViewById(R.id.rv_board_heart);
        rv_board_heart_img = findViewById(R.id.rv_board_heart_img);
        rv_board_heart_text = findViewById(R.id.rv_board_heart_text);

        rv_board_alert = findViewById(R.id.rv_board_alert);
        rv_board_alertBtn = findViewById(R.id.rv_board_alertBtn);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        rv_board_delete.setVisibility(View.INVISIBLE);

        if(intent.getStringExtra("rv_board_WriteMember_id").equals(member.getMember_id())) {
            // 글 작성자가 자기 글을 신고버튼 누르는일은 없으므로
            rv_board_report.setVisibility(View.GONE);

            rv_board_delete.setVisibility(View.VISIBLE);

        }
    }

    public void setEvents() {
        final Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String member_gson = sharedPreferences.getString("info", null);
        final Member member = gson.fromJson(member_gson, Member.class);
        final Intent intent = getIntent();
        final String member_id = "member_id=" + intent.getStringExtra("rv_board_WriteMember_id");
        Log.d("aaa",member_id);
        memberInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url1 = new URL(ip + "/JS/android/rv_board/findByRv_board");
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.getOutputStream().write(member_id.getBytes());
                            if (httpURLConnection.getResponseCode() == 200) {
                                BufferedReader br1 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                StringBuffer sb1 = new StringBuffer();
                                String temp1;
                                while ((temp1 = br1.readLine()) != null) {
                                    sb1.append(temp1);
                                }
                                final String JsonMember = sb1.toString();
                                member1 = gson.fromJson(JsonMember, Member.class);
                                Log.d("member1",member1.toString());

                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent1 = new Intent(Rv_boardSelected.this, clickAlertUserPage.class);
                                    intent1.putExtra("member_id", member1.getMember_id());
                                    intent1.putExtra("member_name", member1.getMember_name());
                                    intent1.putExtra("member_profile_pic", member1.getMember_profile_pic());
                                    intent1.putExtra("member_gender", member1.getMember_gender());
                                    intent1.putExtra("member_birthday", member1.getMember_birthday());
                                    intent1.putExtra("member_Zip_code",member1.getMember_Zip_code());
                                    intent1.putExtra("member_Street_address",member1.getMember_Street_name_address());
                                    intent1.putExtra("member_detailed_address",member1.getMember_Detailed_Address());
                                    startActivity(intent1);
                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                });
            }
        });

        // 짧게누름
        // 리사이클뷰의 각 영역 클릭시 클릭이벤트 처리
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            //
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {

                final String member_id = "member_id=" + alertArrayList.get(position).getMember_id();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url1 = new URL(ip + "/JS/android/rv_board/findByAlert");
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.getOutputStream().write(member_id.getBytes());
                            if (httpURLConnection.getResponseCode() == 200) {
                                BufferedReader br1 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                StringBuffer sb1 = new StringBuffer();
                                String temp1;
                                while ((temp1 = br1.readLine()) != null) {
                                    sb1.append(temp1);
                                }
                                final String JsonMember = sb1.toString();
                                member1 = gson.fromJson(JsonMember, Member.class);
Log.d("member1",member1.toString());

                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent1 = new Intent(Rv_boardSelected.this, clickAlertUserPage.class);
                                    intent1.putExtra("member_id", member1.getMember_id());
                                    intent1.putExtra("member_name", member1.getMember_name());
                                    intent1.putExtra("member_profile_pic", member1.getMember_profile_pic());
                                    intent1.putExtra("member_gender", member1.getMember_gender());
                                    intent1.putExtra("member_birthday", member1.getMember_birthday());
                                    intent1.putExtra("member_Zip_code",member1.getMember_Zip_code());
                                    intent1.putExtra("member_Street_address",member1.getMember_Street_name_address());
                                    intent1.putExtra("member_detailed_address",member1.getMember_Detailed_Address());
                                    startActivity(intent1);
                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                });


            }
        });


        // 길게누름
        // 열기 다이얼로그 창 생성할 예정
        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
                /*
                Intent intent = new Intent(getActivity(),Rv_boardSelected.class);
                intent.putExtra("rv_board_index",Rv_boardArrayList.get(position).getRv_board_index());
                startActivity(intent);
                */
                View report_list = getLayoutInflater().inflate(R.layout.rv_board_report_reason, null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(Rv_boardSelected.this);
                radioGroup = report_list.findViewById(R.id.radioGroup);
                radioButton = report_list.findViewById(R.id.radioButton);
                radioButton2 = report_list.findViewById(R.id.radioButton2);
                radioButton3 = report_list.findViewById(R.id.radioButton3);

                // 라디오버튼 처음 초기값이 욕설 및 비방에 check 되어있으므로
                rv_board_report_result = "욕설 및 비방";

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.radioButton:
                                rv_board_report_result = "욕설 및 비방";

                                break;
                            case R.id.radioButton2:
                                rv_board_report_result = "부적절한 콘텐츠";

                                break;
                            case R.id.radioButton3:
                                rv_board_report_result = "광고";

                                break;
                        }
                    }
                });

                dialog.setTitle("댓글");
                dialog.setView(report_list);
                if(member.getMember_id().equals(alertArrayList.get(position).getMember_id())) {
                    dialog.setPositiveButton("댓글삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String alert_index = "alert_index=" +alertArrayList.get(position).getAlert_index();
                                        URL url1 = new URL(ip + "/JS/android/rv_board/alert/delete");
                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setDoOutput(true);
                                        httpURLConnection.getOutputStream().write(alert_index.getBytes());
                                        httpURLConnection.getResponseCode();

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent1 = getIntent();
                                                finish();
                                                startActivity(intent1);
                                            }
                                        });


                                    } catch (Exception e) {

                                    }
                                }
                            });
                        }
                    });
                }

                dialog.setNegativeButton("신고", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {

                                try {

                                    String alert_index = "alert_index=" + alertArrayList.get(position).getAlert_index();
                                    String alert_reason = "&alert_reason=" + alertArrayList.get(position).getAlert_reason();
                                    String report_reason = "&report_reason=" + rv_board_report_result;
                                    String report_member_id = "&report_member_id=" + alertArrayList.get(position).getMember_id();
                                    String reporter_member_id = "&reporter_member_id=" + member.getMember_id();


                                    URL url1 = new URL(ip + "/JS/android/report");
                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                                    httpURLConnection.setRequestMethod("POST");
                                    httpURLConnection.setDoOutput(true);
                                    httpURLConnection.getOutputStream().write(alert_index.getBytes());
                                    httpURLConnection.getOutputStream().write(alert_reason.getBytes());
                                    httpURLConnection.getOutputStream().write(report_reason.getBytes());
                                    httpURLConnection.getOutputStream().write(report_member_id.getBytes());
                                    httpURLConnection.getOutputStream().write(reporter_member_id.getBytes());

                                    if (httpURLConnection.getResponseCode() == 200) {
                                        BufferedReader br1 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                        StringBuffer sb1 = new StringBuffer();
                                        String temp1;
                                        while ((temp1 = br1.readLine()) != null) {
                                            sb1.append(temp1);
                                        }
                                        final String alreadyReport = sb1.toString();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                switch (alreadyReport) {
                                                    case "{\"msg\":\"alreadyReport\"}":
                                                        Toast.makeText(Rv_boardSelected.this, "이미 신고하신 댓글입니다", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    case "{\"msg\":\"reportSuc\"}":
                                                        Toast.makeText(Rv_boardSelected.this, "신고 완료", Toast.LENGTH_SHORT).show();
                                                        break;

                                                }
                                            }
                                        });
                                    }

                                } catch (Exception e) {

                                }
                            }
                        });

                    }
                });

                dialog.show();
                return true;
            }

        });


        // 게시글 삭제
        rv_board_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Rv_boardSelected.this);
                dialog.setTitle("게시글 삭제");
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String rv_board_index = "rv_board_index="+intent.getIntExtra("rv_board_index",0);
                                    URL url1 = new URL(ip+"/JS/android/rv_board/delete");
                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                                    httpURLConnection.setRequestMethod("POST");
                                    httpURLConnection.setDoOutput(true);
                                    httpURLConnection.getOutputStream().write(rv_board_index.getBytes());
                                    httpURLConnection.getResponseCode();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent1 = new Intent(getApplicationContext(),UserActivity.class);
                                            startActivity(intent1);
                                        }
                                    });





                        }catch (Exception e){

                        }
                            }
                        });
                    }
                });
                dialog.show();
            }
        });

        // 게시글 신고
        rv_board_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(member.getMember_id() == null) {
                Intent intent1 = new Intent(getApplicationContext(),LoginActivity.class);
                Toast.makeText(Rv_boardSelected.this,"로그인이 필요한 서비스입니다",Toast.LENGTH_LONG).show();
                startActivity(intent1);
                }
View report_list = getLayoutInflater().inflate(R.layout.rv_board_report_reason,null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(Rv_boardSelected.this);
                radioGroup = report_list.findViewById(R.id.radioGroup);
                radioButton = report_list.findViewById(R.id.radioButton);
                radioButton2 = report_list.findViewById(R.id.radioButton2);
                radioButton3 = report_list.findViewById(R.id.radioButton3);

                // 라디오버튼 처음 초기값이 욕설 및 비방에 check 되어있으므로
                rv_board_report_result = "욕설 및 비방";

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.radioButton :
                                rv_board_report_result = "욕설 및 비방";

                                break;
                            case R.id.radioButton2 :
                                rv_board_report_result = "부적절한 콘텐츠";

                                break;
                            case R.id.radioButton3 :
                                rv_board_report_result = "광고";

                                break;
                        }
                    }
                });

                dialog.setTitle("게시글 신고");
                dialog.setView(report_list);
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton("신고", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {

                                try{

                                    String rv_board_index = "rv_board_index="+intent.getIntExtra("rv_board_index",0);
                                    String rv_board_title = "&rv_board_title="+intent.getStringExtra("rv_board_title");
                                    String rv_board_content ="&rv_board_content="+intent.getStringExtra("rv_board_content");
                                    String rv_board_picture = "&rv_board_picture="+intent.getStringExtra("rv_board_picture");
                                    String report_reason = "&report_reason="+rv_board_report_result;
                                    String report_member_id = "&report_member_id="+intent.getStringExtra("rv_board_WriteMember_id");
                                    String reporter_member_id = "&reporter_member_id="+member.getMember_id();


                                    URL url1 = new URL(ip+"/JS/android/report");
                                    HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
                                    httpURLConnection.setRequestMethod("POST");
                                    httpURLConnection.setDoOutput(true);
                                    httpURLConnection.getOutputStream().write(rv_board_index.getBytes());
                                    httpURLConnection.getOutputStream().write(rv_board_title.getBytes());
                                    httpURLConnection.getOutputStream().write(rv_board_content.getBytes());
                                    httpURLConnection.getOutputStream().write(rv_board_picture.getBytes());
                                    httpURLConnection.getOutputStream().write(report_reason.getBytes());
                                    httpURLConnection.getOutputStream().write(report_member_id.getBytes());
                                    httpURLConnection.getOutputStream().write(reporter_member_id.getBytes());

                                    if(httpURLConnection.getResponseCode()==200){
                                        BufferedReader br1 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                        StringBuffer sb1 = new StringBuffer();
                                        String temp1 ;
                                        while((temp1 = br1.readLine())!= null){
                                            sb1.append(temp1);
                                        }
                                        final String alreadyReport = sb1.toString();
                                        Log.d("aaa:",alreadyReport);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                        switch(alreadyReport) {
                                            case "{\"msg\":\"alreadyReport\"}":
                                                Toast.makeText(Rv_boardSelected.this, "이미 신고하신 게시글입니다", Toast.LENGTH_SHORT).show();
                                                break;
                                            case "{\"msg\":\"reportSuc\"}":



                                                Toast.makeText(Rv_boardSelected.this, "신고 완료", Toast.LENGTH_SHORT).show();
                                                break;

                                        }
                                            }
                                        });
                                    }

                                }catch (Exception e){

                                }
                            }
                        });
                    }
                });
                dialog.show();
            }
        });

        // 댓글 입력하기
        rv_board_alertBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            if(member == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"로그인이 필요한 서비스입니다",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Rv_boardSelected.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }

                            String rv_board_index = "rv_board_index="+intent.getIntExtra("rv_board_index",0);
                            String rv_board_alert1 = "&alert_reason="+rv_board_alert.getText().toString();
                            String member_id = "&member_id="+member.getMember_id();
                            String member_profile = "&member_profile="+member.getMember_profile_pic();


                            URL url1 = new URL(ip+"/JS/android/rv_board/insertAlert");
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.getOutputStream().write(rv_board_index.getBytes());
                            httpURLConnection.getOutputStream().write(rv_board_alert1.getBytes());
                            httpURLConnection.getOutputStream().write(member_id.getBytes());
                            httpURLConnection.getOutputStream().write(member_profile.getBytes());

                            httpURLConnection.getResponseCode();

                            rv_board_alert.setText("");

                        }catch (Exception e){

                        }
                    }
                });

                // 댓글 입력 시 바로 화면 새로고침 시키는 코드
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        rv_board_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            if(member == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"로그인이 필요한 서비스입니다",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Rv_boardSelected.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }

                            String rv_board_index = "rv_board_index="+intent.getIntExtra("rv_board_index",0);
                            String member_id = "&member_id="+member.getMember_id();



                            URL url1 = new URL(ip+"/JS/android/rv_board/favorite");
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.getOutputStream().write(rv_board_index.getBytes());
                            httpURLConnection.getOutputStream().write(member_id.getBytes());


                            if(httpURLConnection.getResponseCode()==200){
                                BufferedReader br1 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                StringBuffer sb1 = new StringBuffer();
                                String temp1 ;
                                while((temp1 = br1.readLine())!= null){
                                    sb1.append(temp1);
                                }
                                final String status = sb1.toString();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {


                                switch (status){
                                    case "{\"msg\":\"good\"}":
                                        Toast.makeText(getApplicationContext(),"좋아요",Toast.LENGTH_SHORT).show();
                                        break;

                                    case "{\"msg\":\"bad\"}":
                                        Toast.makeText(getApplicationContext(),"좋아요 취소",Toast.LENGTH_SHORT).show();


                                }
                                    }
                                });
                            }



                        }catch (Exception e){

                        }
                    }
                });
               // 좋아요 클릭 시 바로 화면 새로고침
                // 추후에 비동기통신으로 설정
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    public void load(){
        final Intent intent = getIntent();



        // defaultValue 는 getIntExtra 했을때 값이 없는경우 임시로 defaultValue값을 넣어준다
        final String rv_board_index = "rv_board_index="+intent.getIntExtra("rv_board_index",0);
        Log.d("rv_board_index",rv_board_index);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(ip+"/JS/android/rv_board/selected");
                    HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();


                    httpUrlConnection.setRequestMethod("POST");
                    httpUrlConnection.setDoInput(true);
                    httpUrlConnection.getOutputStream().write(rv_board_index.getBytes());



                    if (httpUrlConnection.getResponseCode() == 200) {

                        BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                        StringBuffer sb = new StringBuffer();
                        String temp = null;
                        while((temp = br.readLine())!=null){
                            sb.append(temp);
                        }
                        String JsonRv_board = sb.toString();
                        Gson gson = new Gson();
                        final Rv_board rv_board = gson.fromJson(JsonRv_board,Rv_board.class);

                        rv_board_write_date.setText(rv_board.getRv_board_post_date());
                        rv_board_location.setText(rv_board.getRv_board_location());
                        write_member_id.setText(rv_board.getMember_id());
                        rv_board_watch.setText(Integer.toString(rv_board.getRv_board_count()));
                        rv_board_title.setText(rv_board.getRv_board_title());
                        rv_board_comments.setText(Integer.toString(rv_board.getRv_board_comments()));

                        rv_board_heart_text.setText(Integer.toString(rv_board.getRv_board_heart()));


                        // 글쓴멤버 프로필사진 불러오기
                        try {
                            URL url1 = new URL(ip+rv_board.getMember_profile());
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
                            InputStream is = httpURLConnection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            Bitmap bitmap1 = MakeImageViewRounding.getRoundedCornerBitmap(bitmap);
                            Log.d("bitmap:",bitmap.toString());

                            write_member_profile.setImageBitmap(bitmap);
                        }catch(Exception e){

                        }

                        // 글 사진 불러오기
                        try {

                            URL url1 = new URL(ip+rv_board.getRv_board_picture());
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();

                            InputStream inputStream = httpURLConnection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            Log.d("bitmap2",bitmap.toString());
                            rv_board_image.setImageBitmap(bitmap);


                        }catch (Exception e){

                        }


                        // 글 댓글 수 가져오기
                        try{
                            URL url1 = new URL(ip+"/JS/android/rv_board/count");
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();

                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoInput(true);
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.getOutputStream().write(rv_board_index.getBytes());
                            if(httpURLConnection.getResponseCode()==200){
                                BufferedReader br1 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                StringBuffer sb1 = new StringBuffer();
                                String temp1 ;
                                while((temp1 = br1.readLine())!= null){
                                    sb1.append(temp1);
                                }
                                String loadAlertCount = sb1.toString();
                                Gson gson1 = new Gson();
                                Rv_board rvBoard = gson1.fromJson(loadAlertCount,Rv_board.class);
                                rv_board_comments.setText(Integer.toString(rvBoard.getRv_board_comments()));
                            }
                        }catch (Exception e){

                        }


                        // 글 좋아요 갯수 불러오기
                        try{
                            URL url1 = new URL(ip+"/JS/android/rv_board/favoriteCount");
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();

                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoInput(true);
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.getOutputStream().write(rv_board_index.getBytes());
                            if(httpURLConnection.getResponseCode()==200){
                                BufferedReader br1 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                StringBuffer sb1 = new StringBuffer();
                                String temp1 ;
                                while((temp1 = br1.readLine())!= null){
                                    sb1.append(temp1);
                                }
                                String loadHeartCount = sb1.toString();
                                Gson gson1 = new Gson();
                                Rv_board rvBoard = gson1.fromJson(loadHeartCount,Rv_board.class);
                                rv_board_heart_text.setText(Integer.toString(rvBoard.getRv_board_heart()));
                            }
                        }catch (Exception e){

                        }

                        // 글 좋아요 확인
                        try{

                            SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                            String member_gson = sharedPreferences.getString("info", null);
                            final Member member = gson.fromJson(member_gson, Member.class);

                            String rv_board_index = "rv_board_index="+intent.getIntExtra("rv_board_index",0);
                            String member_id = "&member_id="+member.getMember_id();



                            URL url1 = new URL(ip+"/JS/android/rv_board/favorite/check");
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.getOutputStream().write(rv_board_index.getBytes());
                            httpURLConnection.getOutputStream().write(member_id.getBytes());


                            if(httpURLConnection.getResponseCode()==200){
                                BufferedReader br1 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                StringBuffer sb1 = new StringBuffer();
                                String temp1 ;
                                while((temp1 = br1.readLine())!= null){
                                    sb1.append(temp1);
                                }
                                String loadHeartCount = sb1.toString();

                                switch (loadHeartCount){
                                    // 좋아요
                                    case "{\"favorite\":\"true\"}" :
                                        rv_board_heart_img.setImageResource(R.drawable.ic_favorite_red_24dp);
                                        break;

                                    // 좋아요 x
                                    case "{\"favorite\":\"false\"}":
                                        rv_board_heart_img.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                                        break;
                                }
                            }
                        }catch (Exception e){

                        }



                        // 글 댓글 불러오기
                        try{
                            URL url1 = new URL(ip+"/JS/android/rv_board/loadAlert");
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.setDoInput(true);

                            httpURLConnection.getOutputStream().write(rv_board_index.getBytes());

                            if(httpURLConnection.getResponseCode() == 200){
                                BufferedReader br1 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                StringBuffer sb1 = new StringBuffer();
                                String temp1 ;
                                while((temp1 = br1.readLine())!= null){
                                    sb1.append(temp1);
                                }
                                String loadAlert = sb1.toString();

                                JSONArray jsonArray = new JSONArray(loadAlert);
                                ArrayList<Alert> alertList = new ArrayList<>();
                                for(int i = 0 ; i < jsonArray.length() ; i++){
                                    Alert alert = new Alert();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    alert.setAlert_index(jsonObject.getInt("alert_index"));
                                    alert.setRv_board_index(jsonObject.getInt("rv_board_index"));
                                    alert.setMember_id(jsonObject.getString("member_id"));
                                    if(jsonObject.has("member_profile")) {
                                        alert.setMember_profile(jsonObject.getString("member_profile"));
                                    }
                                    alert.setAlert_reason(jsonObject.getString("alert_reason"));
                                    alert.setAlert_date(jsonObject.getString("alert_date"));
                                    alertList.add(alert);


                                }

                                alertArrayList = new ArrayList<>();
                                if(alertList != null) {
                                    Log.d("inRunOn:",alertList.toString());
                                    for (int i = 0; i < alertList.size(); i++) {
                                        alertArrayList.add(new Alert(alertList.get(i).getAlert_index(),alertList.get(i).getRv_board_index(),alertList.get(i).getMember_id(),alertList.get(i).getMember_profile(),alertList.get(i).getAlert_reason(),alertList.get(i).getAlert_date()));
                                    }

                                    RecyclerAdapter_alert myAdapter = new RecyclerAdapter_alert(alertArrayList);

                                    recyclerView.setAdapter(myAdapter);
                                }

                            }
                        }catch (Exception e){

                        }





                    }
                }catch(Exception e){

                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_board_selected);
        setRefs();
        setEvents();
        load();


    }




}
