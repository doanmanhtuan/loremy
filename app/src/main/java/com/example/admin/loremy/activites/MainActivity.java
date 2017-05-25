package com.example.admin.loremy.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.loremy.R;

public class MainActivity extends AppCompatActivity {

    TextView tvLostPass, tvCreateNewAcc;
    Button btnLogin, btnLoginWithFacebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();
    }

    private void init() {

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLoginWithFacebook = (Button) findViewById(R.id.btnLoginWithFacebook);
        tvLostPass = (TextView) findViewById(R.id.tvLostPass);
        tvCreateNewAcc = (TextView) findViewById(R.id.tvCreatNewAccount);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Activity_DangNhap_2.class);
                startActivity(in);
            }
        });
        tvLostPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Lost_Pass.class);
                startActivity(intent);
            }
        });
        tvCreateNewAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Create_New_Acc.class);
                startActivity(intent);
            }
        });
    }
}
