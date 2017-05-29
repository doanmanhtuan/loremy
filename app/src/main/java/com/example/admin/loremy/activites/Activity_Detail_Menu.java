package com.example.admin.loremy.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.loremy.R;
import com.example.admin.loremy.service.CommonUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Admin on 25/5/2017.
 */

public class Activity_Detail_Menu extends AppCompatActivity {
    public static final String TAG = "TUAN";
    Activity activity;
    public String price, url;
    @Bind(R.id.imgAvatar)
    ImageView imgAvatar;
    @Bind(R.id.tvpPrice)
    TextView tvpPrice;
    @Bind(R.id.tvDescription)
    TextView tvDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);
        ButterKnife.bind(this);
        activity = Activity_Detail_Menu.this;
        Bundle bundle = getIntent().getExtras();
        price = bundle.getString("price");
        url = bundle.getString("imgurl");
        Log.d(TAG, "onCreate: "+ price + "/n"+ url);
        setData();
    }

    public void setData(){
            tvpPrice.setText(CommonUtils.stringVietNamCurrency(Double.parseDouble(price)));
            Glide.with(activity).load(url).into(imgAvatar);
    }
}
