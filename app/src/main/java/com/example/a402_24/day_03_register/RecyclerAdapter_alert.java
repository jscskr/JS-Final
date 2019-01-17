package com.example.a402_24.day_03_register;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecyclerAdapter_alert extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String ip ="http://172.30.1.21:8080";

    // 4번째 실행
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView alert_member_profile;
        TextView alert_member_id;

        TextView alert_date;
        TextView alert_member_comments;


        MyViewHolder(View itemView) {
            super(itemView);
            alert_member_profile = itemView.findViewById(R.id.alert_member_profile);
            alert_member_id = itemView.findViewById(R.id.alert_member_id);
            alert_date = itemView.findViewById(R.id.alert_date);
            alert_member_comments = itemView.findViewById(R.id.alert_member_comments);

            Log.d("실행1","실행1");
        }



    }
    // 1번째 실행
    private ArrayList<Alert> alertArrayList;
    RecyclerAdapter_alert(ArrayList<Alert> alertArrayList){
        this.alertArrayList = alertArrayList;
        Log.d("실행2","실행2");
    }



    // cardView 의 height 설정
    public static int getDpToPixel(Context context) {
        float px = 0;
        float bottomMargin;
        try {
            px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics());
        } catch (Exception e) {

        }
        return (int) px;
    }


    // cardView 의 margin
    public static int getDpToPixel2(Context context) {
        float bottomMargin = 0;
        try {
            bottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.onlylayout_cardview_alert,null,false);
        RecyclerView.LayoutParams recyclerView =  new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getDpToPixel(v.getContext()));
        recyclerView.bottomMargin = getDpToPixel2(v.getContext());
        recyclerView.topMargin = getDpToPixel2(v.getContext());
        recyclerView.rightMargin = getDpToPixel2(v.getContext());
        recyclerView.leftMargin = getDpToPixel2(v.getContext());
        v.setLayoutParams(recyclerView);
        Log.d("실행3","실행3");



        return new RecyclerAdapter_alert.MyViewHolder(v);
    }


    // 마지막(5번째) 실행 >> 그 후로 2번 제외되고 5>3>1>4 순서로 실행
    // 안에 내용을 설정해준다
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        Log.d("실행4","실행4");
        final RecyclerAdapter_alert.MyViewHolder myViewHolder = (RecyclerAdapter_alert.MyViewHolder) viewHolder;

        // 댓글 작성 회원이름 불러오기
        myViewHolder.alert_member_id.setText(alertArrayList.get(i).getMember_id());

        // 댓글 작성 날짜 불러오기
        myViewHolder.alert_date.setText(alertArrayList.get(i).getAlert_date());

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {








                    // 멤버 프로필사진 불러오기
                    final String aaa = alertArrayList.get(i).getMember_profile();

                    try {
                        URL url = new URL(ip+aaa);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                        InputStream inputStream = httpURLConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Bitmap bitmap1 = MakeImageViewRounding.getRoundedCornerBitmap(bitmap);

                        Log.d("bitmap1", bitmap.toString());

                        myViewHolder.alert_member_profile.setImageBitmap(bitmap);

                    } catch (Exception e) {

                    }




            }

        });


        // 댓글 내용
        myViewHolder.alert_member_comments.setText(alertArrayList.get(i).getAlert_reason());











    }

    // 2번째 실행
    @Override
    public int getItemCount() {
        Log.d("실행5","실행5");
        return alertArrayList.size();
    }
}
