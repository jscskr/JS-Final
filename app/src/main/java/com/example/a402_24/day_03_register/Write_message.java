package com.example.a402_24.day_03_register;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Write_message extends AppCompatActivity {
    private static final String ip ="http://172.30.1.21:8080";
    public static final String MyPREFERENCES = "MyPrefs";
    String result;
    // 핸드폰 앨범에서 선택한 이미지의 절대경로
    String absolutePath;
    // 앨범에서 선택시에 식별하기위한 requestcode
    final static int GET_GALLERY_IMAGE =1;
    EditText message_receiver;
    TextView fileName;
    EditText message_title;
    EditText message_content;
    Button album_select_btn;
    Button send;
    ImageView fileImage;
    public void setRefs(){
        message_receiver = findViewById(R.id.message_receiver);
        fileName = findViewById(R.id.textView4);
        message_title = findViewById(R.id.message_title);
        message_content = findViewById(R.id.message_content);
        album_select_btn = findViewById(R.id.album_select_btn);
        send = findViewById(R.id.send_btn);
        fileImage = findViewById(R.id.fileImage);
    }
    public void setEvents(){
        send.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    public void run() {

                        try {
                            String member_gson;
                            Gson gson = new Gson();
                            SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                            member_gson = sharedPreferences.getString("info", null);
                            final Member member = gson.fromJson(member_gson, Member.class);

                            String attachmentFileName = null;
                            if(absolutePath != null) {
                                // 앞에 경로 다 지우고 이미지 이름 ~.jpg
                                attachmentFileName  = absolutePath.substring(32);
                                Log.d("CheckImage", Integer.toString(absolutePath.length()));
                            }


                            String crlf = "\r\n";
                            String twoHyphens = "--";
                            String boundary = "*****";

                            URL endPoint =
                                    new URL(ip+"/JS/android/sendMessage");
                            HttpURLConnection myConnection =
                                    (HttpURLConnection) endPoint.openConnection();
                            myConnection.setRequestMethod("POST");
                            myConnection.setDoOutput(true);
                            myConnection.setDoInput(true);
                            // key, value 형태 // Content-Type: multipart/form-data; boundary=*****
                            myConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);


                            DataOutputStream request = new DataOutputStream(myConnection.getOutputStream());

                            // 프로필 파일 넘기기
                            // --*****\r\n
                            // Content-Disposition: form-data; name="file" ;
                            //
                            // 파일 값
                            //

                            // 0이 아닐때만 보내도록 // 이미지 첨부하지 않은때도 있으므로
                            if(absolutePath != null) {
                                request.writeBytes(twoHyphens + boundary + crlf);
                                request.writeBytes("Content-Disposition: form-data; name= \"file\" ;filename=\"" + attachmentFileName + "\"" + crlf);

                                request.writeBytes(crlf);
                                FileInputStream fStream = new FileInputStream(absolutePath);
                                byte buffer5[] = new byte[1024];
                                int length = -1;
                                while ((length = fStream.read(buffer5)) != -1) {
                                    request.write(buffer5, 0, length);
                                }
                                request.writeBytes(crlf);
                            }

                            // 받는 사람
                            request.writeBytes(twoHyphens + boundary + crlf);
                            request.writeBytes("Content-Disposition: form-data; name=\"member_receiver\"" + crlf);
                            request.writeBytes(crlf);
                            request.writeBytes(message_receiver.getText().toString());
                            request.writeBytes(crlf);

                            // 메시지 제목
                            request.writeBytes(twoHyphens + boundary + crlf);
                            request.writeBytes("Content-Disposition: form-data; name=\"message_title\"" + crlf);
                            request.writeBytes(crlf);
                            request.writeBytes(message_title.getText().toString());
                            request.writeBytes(crlf);

                            // 메시지 내용
                            request.writeBytes(twoHyphens+boundary+crlf);
                            request.writeBytes("Content-Disposition: form-data; name=\"message_content\""+crlf);
                            request.writeBytes(crlf);
                            request.writeBytes(message_content.getText().toString());
                            request.writeBytes(crlf);

                            // 보내는사람 아이디
                            request.writeBytes(twoHyphens+boundary+crlf);
                            request.writeBytes("Content-Disposition: form-data; name=\"member_id\""+crlf);
                            request.writeBytes(crlf);
                            request.writeBytes(member.getMember_id());
                            request.writeBytes(crlf);
                            // 보내느사람 프로필
                            request.writeBytes(twoHyphens+boundary+crlf);
                            request.writeBytes("Content-Disposition: form-data; name=\"member_profile_pic\""+crlf);
                            request.writeBytes(crlf);
                            request.writeBytes(member.getMember_profile_pic());
                            request.writeBytes(crlf);

                            // 마지막에 닫어줘야한다 // 끝태그
                            request.writeBytes(twoHyphens + boundary);

                            if(myConnection.getResponseCode()== 200) {

                                BufferedReader br = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
                                StringBuffer sb = new StringBuffer();
                                String temp = null;
                                while ((temp = br.readLine()) != null) {
                                    sb.append(temp);
                                }
                                result = sb.toString();
                                Log.d("resulttt", result);


                                        Intent intent = new Intent(getApplicationContext(),Message_result.class);
                                        intent.putExtra("result",result);
                                        startActivity(intent);
finish();


                            }


                        } catch (Exception e) {
                            Log.d("LOG_L",e.getMessage());
                        }
                    }
                });

            }

        });
        album_select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        switch (requestCode){

            case GET_GALLERY_IMAGE :
                // 사진 선택안하고 그냥 뒤로가기 누르는상황도 있으므로
                if(data != null) {
                    // 선택한 사진이 보이도록
                    fileImage.setImageURI(data.getData());
                    absolutePath = getRealPathFromURI(data.getData());
                    String messageViewFileName = absolutePath.substring(32);
                    fileName.setText(messageViewFileName);
                }
                break;

        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);
        setRefs();
        setEvents();

    }

    // 식별자경로에서 실제 경로로 변경해줌
    private String getRealPathFromURI(Uri contentUri) {
        Log.d("aaaaa",contentUri.toString());
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToFirst();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        return cursor.getString(column_index);
    }
}