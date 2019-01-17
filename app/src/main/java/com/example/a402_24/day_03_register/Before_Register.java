package com.example.a402_24.day_03_register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Before_Register extends AppCompatActivity {

    Button btn_register;
    TextView btn_login;

    public void initRefs(){
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (TextView) findViewById(R.id.btn_login);
    }

    public void setEvents(){

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before__register);

        initRefs();
        setEvents();
    }
}
