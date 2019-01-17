package com.example.a402_24.day_03_register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class reportList extends AppCompatActivity {

    private static final String ip ="http://172.30.1.21:8080";
    public static final String MyPREFERENCES = "MyPrefs";













    public void setRefs(){

        Intent intent =getIntent();

        final Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String member_gson = sharedPreferences.getString("info", null);
        final Member member = gson.fromJson(member_gson, Member.class);


        final LinearLayout mainLayout = findViewById(R.id.reportList1);
        if(intent.getStringExtra("type").equals("rv_board")){

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(ip+"/JS/android/reportList/Rv_board");
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
                            Log.d("aaa111",JsonMember);
                            final ArrayList<Report> reportList = new ArrayList<>();
                            for( int i = 0 ; i < jsonArray.length() ; i++){

                                Report report = new Report();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                report.setRv_board_index(jsonObject.getInt("rv_board_index"));
                                if(jsonObject.has("rv_board_title")) {
                                    report.setRv_board_title(jsonObject.getString("rv_board_title"));
                                }
                                if(jsonObject.has("rv_board_content")) {
                                    report.setRv_board_content(jsonObject.getString("rv_board_content"));
                                }
                                if(jsonObject.has("rv_board_picture")) {
                                    report.setRv_board_picture(jsonObject.getString("rv_board_picture"));
                                }
                                report.setReport_reason(jsonObject.getString("report_reason"));
                                report.setReport_member_id(jsonObject.getString("report_member_id"));
                                report.setReporter_member_id(jsonObject.getString("reporter_member_id"));
                                report.setReport_date(jsonObject.getString("report_date"));
reportList.add(report);

                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if(reportList != null) {

                                        for (int i = 0; i < reportList.size(); i++) {
                                            if (reportList.get(i).getRv_board_index() != -1) {
// dp 설정
                                                Bitmap bitmap = null;
                                                final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                                                final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);


                                                TextView rv_board_index = new TextView(reportList.this);
                                                TextView rv_board_title = new TextView(reportList.this);
                                                TextView rv_board_content = new TextView(reportList.this);
                                                TextView rv_board_picture = new TextView(reportList.this);
                                                TextView report_reason = new TextView(reportList.this);
                                                TextView report_member_id = new TextView(reportList.this);
                                                TextView reporter_member_id = new TextView(reportList.this);
                                                TextView report_date = new TextView(reportList.this);
                                                TextView line = new TextView(reportList.this);



                                                rv_board_index.setText("게시글 번호:" + reportList.get(i).getRv_board_index());
                                                rv_board_title.setText("게시글 제목:"+reportList.get(i).getRv_board_title());
                                                rv_board_content.setText("게시글 내용:"+reportList.get(i).getRv_board_content());
                                                rv_board_picture.setText("게시글 사진:"+reportList.get(i).getRv_board_picture());
                                                report_reason.setText("신고 이유:"+reportList.get(i).getReport_reason());
                                                report_member_id.setText("신고한 아이디:" + reportList.get(i).getReporter_member_id());
                                                reporter_member_id.setText("신고당한 아이디:" + reportList.get(i).getReport_member_id());
                                                report_date.setText("신고날짜:"+reportList.get(i).getReport_date());
                                                line.setText("----------------------------------------------------------------------------------");

                                                mainLayout.addView(rv_board_index);
                                                mainLayout.addView(rv_board_title);
                                                mainLayout.addView(rv_board_content);
                                                mainLayout.addView(rv_board_picture);
                                                mainLayout.addView(report_member_id);
                                                mainLayout.addView(reporter_member_id);
                                                mainLayout.addView(report_reason);
                                                mainLayout.addView(report_date);
                                                mainLayout.addView(line);
                                            }
                                        }

                                    }


                                }
                            });





                        }
                    }catch (Exception e){

                    }
                }
            });
        } else if(intent.getStringExtra("type").equals("alert")){

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(ip+"/JS/android/reportList/Alert");
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

                            final ArrayList<Report> reportList = new ArrayList<>();
                            for( int i = 0 ; i < jsonArray.length() ; i++){

                                Report report = new Report();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                report.setAlert_index(jsonObject.getInt("alert_index"));
                                report.setReport_member_id(jsonObject.getString("report_member_id"));
                                report.setReport_reason(jsonObject.getString("report_reason"));
                                if(jsonObject.has("alert_reason")) {
                                    report.setAlert_reason(jsonObject.getString("alert_reason"));
                                }
                                report.setReporter_member_id(jsonObject.getString("reporter_member_id"));
                                report.setReport_date(jsonObject.getString("report_date"));

                                reportList.add(report);
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if(reportList != null) {

                                        for (int i = 0; i < reportList.size(); i++) {
                                            if (reportList.get(i).getAlert_index() != -1) {
// dp 설정
                                                Bitmap bitmap = null;
                                                final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                                                final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);


                                                TextView alert_index = new TextView(reportList.this);
                                                TextView report_member_id = new TextView(reportList.this);
                                                TextView report_reason = new TextView(reportList.this);
                                                TextView alert_reason = new TextView(reportList.this);
                                                TextView reporter_member_id = new TextView(reportList.this);
                                                TextView report_date = new TextView(reportList.this);

                                                TextView line = new TextView(reportList.this);



                                                alert_index.setText("댓글 번호:" + reportList.get(i).getAlert_index());
                                                report_member_id.setText("신고한 아이디:" + reportList.get(i).getReporter_member_id());
                                                reporter_member_id.setText("신고당한 아이디:" + reportList.get(i).getReport_member_id());
                                                report_reason.setText("신고 이유:"+reportList.get(i).getReport_reason());
                                                alert_reason.setText("댓글 내용:" + reportList.get(i).getMessage_content());
                                                report_date.setText("신고날짜:"+reportList.get(i).getReport_date());
                                                line.setText("----------------------------------------------------------------------------------");

                                                mainLayout.addView(alert_index);
                                                mainLayout.addView(report_member_id);
                                                mainLayout.addView(reporter_member_id);
                                                mainLayout.addView(report_reason);
                                                mainLayout.addView(alert_reason);
                                                mainLayout.addView(report_date);
                                                mainLayout.addView(line);
                                            }
                                        }

                                    }


                                }
                            });



                        }
                    }catch (Exception e){

                    }
                }
            });
        } else {

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(ip+"/JS/android/reportList/Message");
                        final HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
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
                            Log.d("jsonArray",JsonMember);
                            final ArrayList<Report> reportList = new ArrayList<>();
                            for( int i = 0 ; i < jsonArray.length() ; i++){
                                Report report = new Report();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                report.setMessage_id(jsonObject.getInt("message_id"));
                                report.setReport_reason(jsonObject.getString("report_reason"));
                                report.setReport_member_id(jsonObject.getString("report_member_id"));
                                report.setReporter_member_id(jsonObject.getString("reporter_member_id"));
                                report.setReport_date(jsonObject.getString("report_date"));
                                if(jsonObject.has("message_title")) {
                                    report.setMessage_title(jsonObject.getString("message_title"));
                                }
                                if(jsonObject.has("message_content")) {
                                    report.setMessage_content(jsonObject.getString("message_content"));
                                }
                                if(jsonObject.has("message_picture")) {
                                    report.setMessage_picture(jsonObject.getString("message_picture"));
                                }








                                reportList.add(report);
                            }
                            Log.d("내용",reportList.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                            if(reportList != null) {

                                for (int i = 0; i < reportList.size(); i++) {
                                    if (reportList.get(i).getMessage_id() != -1) {
// dp 설정
                                        Bitmap bitmap = null;
                                        final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                                        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);

                                        TextView report_date = new TextView(reportList.this);
                                        TextView report_reason = new TextView(reportList.this);
                                        TextView message_id = new TextView(reportList.this);
                                        TextView member_id_Text = new TextView(reportList.this);
                                        TextView member_password_Text = new TextView(reportList.this);
                                        TextView message_title = new TextView(reportList.this);
                                        TextView message_content = new TextView(reportList.this);
                                        TextView line = new TextView(reportList.this);

                                        TextView imageView = new TextView(reportList.this);

                                        message_id.setText("메시지 번호:" + reportList.get(i).getMessage_id());
                                        member_id_Text.setText("신고한 아이디:" + reportList.get(i).getReporter_member_id());
                                        member_password_Text.setText("신고당한 아이디:" +reportList.get(i).getReport_member_id());
                                        report_reason.setText("신고 이유:"+reportList.get(i).getReport_reason());
                                        message_title.setText("메시지 제목:" + reportList.get(i).getMessage_title());
                                        message_content.setText("메시지 내용:" + reportList.get(i).getMessage_content());
                                        imageView.setText("메시지 사진:"+reportList.get(i).getMessage_picture());
                                        report_date.setText("신고날짜:"+reportList.get(i).getReport_date());
                                        line.setText("----------------------------------------------------------------------------------");
                                        /*
                                        if (reportList.get(i).getMessage_picture() != null) {
                                            try {
                                                Log.d("ㅁㅁㅁ2",reportList.get(i).getMessage_picture());
                                                String aaa =reportList.get(i).getMessage_picture();
                                                URL url = new URL(aaa);
                                                Log.d("ㅁㅁㅁ2",reportList.get(i).getMessage_picture());
                                                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                                                Log.d("bitmap2",bitmap.toString());
                                                InputStream inputStream = httpURLConnection.getInputStream();

                                                Log.d("bitmap",bitmap.toString());
                                                bitmap = BitmapFactory.decodeStream(inputStream);

                                                

                                            } catch (Exception e) {

                                            }
                                        }else {
                                            Log.d("ㅁㅁㅁ","비어있다");
                                        }
                                        imageView.setImageBitmap(bitmap);
*/
                                        mainLayout.addView(message_id);
                                        mainLayout.addView(member_id_Text);
                                        mainLayout.addView(member_password_Text);
                                        mainLayout.addView(report_reason);
                                        mainLayout.addView(message_title);
                                        mainLayout.addView(message_content);
                                        mainLayout.addView(imageView);
                                        mainLayout.addView(report_date);
                                        mainLayout.addView(line);

                                    }
                                }

                            }


                                }
                            });
                        }
                    }catch (Exception e){

                    }
                }
            });
        }



    }

    public void setEvent(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        setRefs();
        setEvent();
    }
}
