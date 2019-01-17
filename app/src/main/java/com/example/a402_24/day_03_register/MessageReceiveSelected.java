package com.example.a402_24.day_03_register;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class MessageReceiveSelected extends AppCompatActivity {
    private static final String ip ="http://172.30.1.21:8080";
    public static final String MyPREFERENCES = "MyPrefs";
    TextView deleteMessage;

    TextView message_report;
    RadioGroup radioGroup;
    String rv_board_report_result;
    RadioButton radioButton;
    RadioButton radioButton2;
    RadioButton radioButton3;

    Bitmap bitmap;
    TextView message_sender;
    TextView message_title;
    TextView message_content;
    ImageView imageView;


    public void setRefs(){
        deleteMessage = findViewById(R.id.deleteMessage);
        message_report = findViewById(R.id.message_report);
        message_sender = findViewById(R.id.message_sender);
        message_title = findViewById(R.id.message_title);
        message_content = findViewById(R.id.message_content);
        imageView = findViewById(R.id.fileImage);

    }

    public void setEvents(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String member_gson = sharedPreferences.getString("info", null);
        final Member member = gson.fromJson(member_gson, Member.class);
        final Intent intent = getIntent();

        // defaultValue 는 getIntExtra 했을때 값이 없는경우 임시로 defaultValue값을 넣어준다
        final String message_id = "message_id="+intent.getIntExtra("message_id",0);
        Log.d("message_id",message_id);
        deleteMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MessageReceiveSelected.this);
                dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    URL url = new URL(ip+"/JS/android/receiveMessageStore/selected/deleteMessage");
                                    HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();


                                    httpUrlConnection.setRequestMethod("POST");
                                    httpUrlConnection.setDoOutput(true);
                                    httpUrlConnection.getOutputStream().write(message_id.getBytes());



                                    httpUrlConnection.getResponseCode();




runOnUiThread(new Runnable() {
    @Override
    public void run() {
        Intent intent1 = new Intent(getApplicationContext(),MessageActivity.class);
        startActivity(intent1);
    }
});

                                }catch(Exception e){

                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                    }
                });
                dialog.setTitle("메시지 삭제");
                dialog.show();

            }
        });

        message_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View report_list = getLayoutInflater().inflate(R.layout.rv_board_report_reason,null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MessageReceiveSelected.this);
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
                dialog.setTitle("메시지 신고");
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

                                    String message_id = "message_id="+intent.getIntExtra("message_id",0);
                                    String message_title = "&message_title="+intent.getStringExtra("message_title");
                                    String message_content ="&message_content="+intent.getStringExtra("message_content");
                                    String message_picture = "&message_picture="+intent.getStringExtra("message_picture");
                                    String report_reason = "&report_reason="+rv_board_report_result;
                                    String report_member_id = "&report_member_id="+intent.getStringExtra("message_WriteMember_id");
                                    String reporter_member_id = "&reporter_member_id="+member.getMember_id();


                                    URL url1 = new URL(ip+"/JS/android/report");
                                    HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
                                    httpURLConnection.setRequestMethod("POST");
                                    httpURLConnection.setDoOutput(true);
                                    httpURLConnection.getOutputStream().write(message_id.getBytes());
                                    httpURLConnection.getOutputStream().write(message_title.getBytes());
                                    httpURLConnection.getOutputStream().write(message_content.getBytes());
                                    httpURLConnection.getOutputStream().write(message_picture.getBytes());
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
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                switch (alreadyReport) {
                                                    case "{\"msg\":\"alreadyReport\"}":
                                                        Toast.makeText(MessageReceiveSelected.this, "이미 신고하신 메시지입니다", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    case "{\"msg\":\"reportSuc\"}":
                                                        Toast.makeText(MessageReceiveSelected.this, "신고 완료", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_receive_selected);
        setRefs();
        setEvents();
        Intent intent = getIntent();

        // defaultValue 는 getIntExtra 했을때 값이 없는경우 임시로 defaultValue값을 넣어준다
        final String message_id = "message_id="+intent.getIntExtra("message_id",0);
        Log.d("message_id",message_id);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(ip+"/JS/android/receiveMessageStore/selected");
                    HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();


                    httpUrlConnection.setRequestMethod("POST");
                    httpUrlConnection.setDoOutput(true);
                    httpUrlConnection.setDoInput(true);
                    httpUrlConnection.getOutputStream().write(message_id.getBytes());



                    if (httpUrlConnection.getResponseCode() == 200) {

                        BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                        StringBuffer sb = new StringBuffer();
                        String temp = null;
                        while((temp = br.readLine())!=null){
                            sb.append(temp);
                        }
                        String JsonMessage = sb.toString();
                        Gson gson = new Gson();
                        final Message message = gson.fromJson(JsonMessage,Message.class);


                        message_sender.setText("보낸 사람 : "+message.getMember_id());
                        message_title.setText("제목 : "+ message.getMessage_title());
                        message_content.setText(message.getMessage_content());


                        try {
                            URL url1 = new URL(message.getMessage_picture());
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
                            InputStream is = httpURLConnection.getInputStream();
                            bitmap = BitmapFactory.decodeStream(is);
                            Log.d("bitmap:",bitmap.toString());
                            imageView.setImageBitmap(bitmap);
                        }catch(Exception e){

                        }




                    }
                }catch(Exception e){

                }
            }
        });


    }
}
