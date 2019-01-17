package com.example.a402_24.day_03_register;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecyclerAdapter_message extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String ip ="http://172.30.1.21:8080";


    // 4번째 실행
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView sendMember_id;
        TextView message_title;
        TextView message_send_date;

        MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_picture);
            sendMember_id = itemView.findViewById(R.id.tv_price);
            message_title = itemView.findViewById(R.id.textView8);
            message_send_date = itemView.findViewById(R.id.textView9);
            Log.d("실행1","실행1");
        }



    }
    // 1번째 실행
    private ArrayList<Message> messageList;
    RecyclerAdapter_message(ArrayList<Message> messageList){
        this.messageList = messageList;
        Log.d("실행2","실행2");
    }


    // cardView 의 margin
    public static int getDpToPixel2(Context context) {
        float bottomMargin = 0;
        try {
            bottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics());
        } catch (Exception e) {

        }
        return (int) bottomMargin;
    }

    // 3번째 실행
    // 여기서 참조할 layout 과 해당 layout 의 크기 조절을 해준다
    // 이 return 값이 위에 MyViewHolder 로 가고
    // AlertDialog.Bulider( 다이얼로그) 에서 해준것처럼
    // 앞에 view 변수명과 함께 .findViewById 를 선언해주면 해당 layout 의 각 id 값을 참조할 수 있다
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.onlylayout_cardview_message,null,false);
        RecyclerView.LayoutParams recyclerView =  new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        recyclerView.topMargin = getDpToPixel2(v.getContext());
        recyclerView.bottomMargin = getDpToPixel2(v.getContext());

        v.setLayoutParams(recyclerView);
        Log.d("실행3","실행3");



        return new MyViewHolder(v);
    }


    // 마지막(5번째) 실행 >> 그 후로 2번 제외되고 5>3>1>4 순서로 실행
    // 안에 내용을 설정해준다
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,  int i) {
        Log.d("실행4","실행4");
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        final String aaa = messageList.get(i).getMessage_profil_pic();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(ip+aaa);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                    myViewHolder.imageView.setImageBitmap(bitmap);


                }catch (Exception e){

                }
            }
        });
        myViewHolder.sendMember_id.setText(messageList.get(i).getMember_id());
        myViewHolder.message_title.setText(messageList.get(i).getMessage_title());
        myViewHolder.message_send_date.setText(messageList.get(i).getMessage_send_date());





    }

    // 2번째 실행
    @Override
    public int getItemCount() {
        Log.d("실행5","실행5");
        return messageList.size();
    }
}
