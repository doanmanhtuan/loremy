package com.example.admin.loremy.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.admin.loremy.R;

/**
 * Created by Admin on 22/5/2017.
 */

public class Activity_DangNhap_2 extends AppCompatActivity {

    Button btnExit, btnChangePass, btnDoHang;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap_2);
//        getSupportActionBar().hide();
        init();
    }

    private void init() {
        btnChangePass = (Button) findViewById(R.id.btnChangePass);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnDoHang = (Button) findViewById(R.id.btnDoHang);
        btnDoHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_DangNhap_2.this, Activity_Menu.class);
                startActivity(intent);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_DangNhap_2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_DangNhap_2.this, Activity_Change_Pass.class);
                startActivity(intent);
            }
        });
    }
}
