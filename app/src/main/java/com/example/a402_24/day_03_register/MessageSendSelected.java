package com.example.a402_24.day_03_register;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class MessageSendSelected extends AppCompatActivity {
    private static final String ip ="http://172.30.1.21:8080";
    TextView deleteMessage;
    Bitmap bitmap;
    TextView message_sender;
    TextView message_title;
    TextView message_content;
    ImageView imageView;


    public void setRefs(){
        deleteMessage = findViewById(R.id.deleteMessage);
        message_sender = findViewById(R.id.message_sender);
        message_title = findViewById(R.id.message_title);
        message_content = findViewById(R.id.message_content);
        imageView = findViewById(R.id.fileImage);

    }

    public void setEvents(){
        Intent intent = getIntent();

        // defaultValue 는 getIntExtra 했을때 값이 없는경우 임시로 defaultValue값을 넣어준다
        final String message_id = "message_id="+intent.getIntExtra("message_id",0);
        Log.d("message_id",message_id);
        deleteMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MessageSendSelected.this);
                dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    URL url = new URL(ip+"/JS/android/sendMessageStore/selected/deleteMessage");
                                    HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();


                                    httpUrlConnection.setRequestMethod("POST");
                                    httpUrlConnection.setDoOutput(true);
                                    httpUrlConnection.getOutputStream().write(message_id.getBytes());



                                    httpUrlConnection.getResponseCode();




                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent1 = new Intent(getApplicationContext(),MessageSendStore.class);
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send_selected);
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
                    URL url = new URL(ip+"/JS/android/sendMessageStore/selected");
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