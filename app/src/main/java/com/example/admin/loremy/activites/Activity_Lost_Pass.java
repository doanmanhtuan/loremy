package com.example.admin.loremy.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.loremy.R;

/**
 * Created by Admin on 23/5/2017.
 */

public class Activity_Lost_Pass extends AppCompatActivity {
    Button btnGuiLaiMK;
    EditText edtEmail;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_pass);
        init();
    }

    private void init() {
        btnGuiLaiMK = (Button) findViewById(R.id.btnGuiLaiMK);
        edtEmail = (EditText) findViewById(R.id.edtEmail_LostPass);
    }
}
