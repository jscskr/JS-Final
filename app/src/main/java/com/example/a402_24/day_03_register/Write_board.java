package com.example.a402_24.day_03_register;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Write_board extends AppCompatActivity {

    private static final String ip ="http://172.30.1.21:8080";
    public static final String MyPREFERENCES = "MyPrefs";
    String member_gson;

    String attachmentFileName;
    String result;
    String StringboardType ;
    String StringboardLocation;
    final static int GET_GALLERY_IMAGE = 1;
    String absolutePath;
    Button album_select_btn;
    EditText board_title;
    EditText board_content;
    Spinner boardType;
    Button address_btn;
    WebView daum_webView;
    TextView daum_result;
    Button address_OK;
    TextView address_text;
    ImageView fileImage;
    TextView fileName;
    Button write_btn;
    Handler handler;

    Gson gson = new Gson();

    public void setRefs(){
        album_select_btn = findViewById(R.id.album_select_btn);
        board_title = findViewById(R.id.editText3);
        board_content = findViewById(R.id.message_content);
        boardType = findViewById(R.id.spinner1);
        address_btn = findViewById(R.id.address_btn);
        address_text = findViewById(R.id.address_text);
        fileImage = findViewById(R.id.fileImage);
        fileName = findViewById(R.id.textView4);
        write_btn = findViewById(R.id.Rv_board_write_btn);

    }

    public void setEvents(){
        album_select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
        boardType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // index 형태 0부터 시작

                switch (position){
                    case 0:
                        StringboardType = "자유 게시판";
                        address_btn.setVisibility(View.INVISIBLE);
                        address_text.setVisibility(View.INVISIBLE);
                        break;

                    case 1 :
                        StringboardType = "리뷰 게시판";
                        address_btn.setVisibility(View.VISIBLE);
                        address_text.setVisibility(View.VISIBLE);
                        break;
                }
                Log.d("position:",StringboardType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialog_address = View.inflate(Write_board.this, R.layout.activity_dialog_address,null);
                final Dialog dialog = new Dialog(Write_board.this);


                daum_webView = (WebView)dialog_address.findViewById(R.id.daum_webview);
                daum_result = dialog_address.findViewById(R.id.daum_result);
                address_OK = dialog_address.findViewById(R.id.button6);

                address_OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                // JavaScript 허용

                daum_webView.getSettings().setJavaScriptEnabled(true);


                // JavaScript의 window.open 허용

                daum_webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


                // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌

                daum_webView.addJavascriptInterface(new AndroidBridge(), "TestApp");


                // web client 를 chrome 으로 설정

                daum_webView.setWebChromeClient(new WebChromeClient());


                // webview url load. php 파일 주소
                daum_webView.loadUrl(ip+"/JS/android/address");


                dialog.setTitle("주소 검색");
                dialog.setContentView(dialog_address);
                dialog.show();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        member_gson = sharedPreferences.getString("info", null);
        final Member member = gson.fromJson(member_gson, Member.class);

        write_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("adsaxcerwe","실행");

                if (absolutePath != null) {
                    // 앞에 경로 다 지우고 이미지 이름 ~.jpg
                    attachmentFileName = absolutePath.substring(32);
                    Log.d("CheckImage", Integer.toString(absolutePath.length()));
                }
                if(address_text.getText().toString().length() == 0){

                    Toast.makeText(Write_board.this.getApplicationContext(), "주소를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;

                }
                if(board_title.getText().toString().length() == 0){

                    Toast.makeText(Write_board.this.getApplicationContext(), "제목을 입력해주세요", Toast.LENGTH_LONG).show();
                    return;

                }
                if(board_content.getText().toString().length() == 0){

                    Toast.makeText(Write_board.this.getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_LONG).show();
                    return;

                }
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {



                            String crlf = "\r\n";
                            String twoHyphens = "--";
                            String boundary = "*****";

                            URL endPoint =
                                    new URL(ip+"/JS/android/rv_board/write");
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
                            if (absolutePath != null) {
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

                            // 작성자 아이디
                            request.writeBytes(twoHyphens + boundary + crlf);
                            request.writeBytes("Content-Disposition: form-data; name=\"member_id\"" + crlf);
                            request.writeBytes(crlf);
                            request.writeBytes(member.getMember_id());
                            request.writeBytes(crlf);

                            // 작성자 프로필사진
                            request.writeBytes(twoHyphens + boundary + crlf);
                            request.writeBytes("Content-Disposition: form-data; name=\"member_profile\"" + crlf);
                            request.writeBytes(crlf);
                            request.writeBytes(member.getMember_profile_pic());
                            request.writeBytes(crlf);

                            // 리뷰 게시판 제목
                            request.writeBytes(twoHyphens + boundary + crlf);
                            request.writeBytes("Content-Disposition: form-data; name=\"rv_board_title\"" + crlf);
                            request.writeBytes(crlf);
                            request.writeBytes(board_title.getText().toString());
                            request.writeBytes(crlf);

                            // 리뷰 게시판 내용
                            request.writeBytes(twoHyphens + boundary + crlf);
                            request.writeBytes("Content-Disposition: form-data; name=\"rv_board_content\"" + crlf);
                            request.writeBytes(crlf);
                            request.writeBytes(board_content.getText().toString());
                            request.writeBytes(crlf);

                            // 리뷰 게시판 위치
                            request.writeBytes(twoHyphens + boundary + crlf);
                            request.writeBytes("Content-Disposition: form-data; name=\"rv_board_location\"" + crlf);
                            request.writeBytes(crlf);
                            // 한글깨짐 방지
                            request.writeBytes(URLEncoder.encode(address_text.getText().toString(),"utf-8"));
                            request.writeBytes(crlf);


                            // 마지막에 닫어줘야한다 // 끝태그
                            request.writeBytes(twoHyphens + boundary);

                            if (myConnection.getResponseCode() == 200) {

                                BufferedReader br = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
                                StringBuffer sb = new StringBuffer();
                                String temp = null;
                                while ((temp = br.readLine()) != null) {
                                    sb.append(temp);
                                }
                                result = sb.toString();
                                Log.d("resulttt", result);


                                Intent intent = new Intent(Write_board.this.getApplicationContext(), Rv_board_result.class);
                                intent.putExtra("result",result);
                                startActivity(intent);
                                finish();



                            }


                        } catch (Exception e) {
                            Log.d("LOG_L", e.getMessage());
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_board);
        setRefs();
        setEvents();
        handler = new Handler();

    }



    public class AndroidBridge {

        @JavascriptInterface

        public void setAddress(final String arg1, final String arg2, final String arg3) {

            handler.post(new Runnable() {

                @Override

                public void run() {


                    daum_result.setText(String.format("(%s) %s %s", arg1, arg2, arg3));
                    address_text.setText(String.format("%s",arg2));
                    // WebView를 초기화 하지않으면 재사용할 수 없음



                }

            });

        }

    }




    // 식별자경로에서 실제 경로로 변경해줌
    private String getRealPathFromURI(Uri contentUri) {
        Log.d("aaaaa",contentUri.toString());
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToFirst();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        return cursor.getString(column_index);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
}
