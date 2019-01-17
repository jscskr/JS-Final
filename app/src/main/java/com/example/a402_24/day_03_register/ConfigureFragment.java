package com.example.a402_24.day_03_register;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigureFragment extends Fragment {

TextView reportBoardList;
TextView reportAlertList;
TextView reportMessageList;

public void setRefs(View v){
    reportBoardList = v.findViewById(R.id.reportBoardList);
    reportAlertList = v.findViewById(R.id.reportAlertList);
    reportMessageList = v.findViewById(R.id.reportMessageList);
}
public void setEvent(View v){
    reportBoardList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),"게시글 선택",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(),reportList.class);
            intent.putExtra("type","rv_board");
            startActivity(intent);
        }
    });

    reportAlertList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),"댓글 선택",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(),reportList.class);
            intent.putExtra("type","alert");
            startActivity(intent);
        }
    });

    reportMessageList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),"메시지 선택",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(),reportList.class);
            intent.putExtra("type","message");
            startActivity(intent);
        }
    });
}
    public ConfigureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_configure, container, false);
        setRefs(v);
        setEvent(v);
        return v;

    }

}
