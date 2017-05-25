package com.example.admin.loremy.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.loremy.data.MenuData;
import com.example.admin.loremy.R;
import com.example.admin.loremy.service.CommonUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by MyPC on 16/06/2016.
 */
public class MenuAdapter extends ArrayAdapter<MenuData> {

    Activity activity;
    ArrayList<MenuData> data;
    @Bind(R.id.imgAvatar)
    ImageView imgAvatar;
    @Bind(R.id.tvPrice)
    TextView tvPrice;

    public MenuAdapter(Activity activity, int resource, ArrayList<MenuData> data) {
        super(activity, resource, data);
        this.activity = activity;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.item_gridview, null);
        }
        ButterKnife.bind(this, convertView);


//        ImageLoader.getInstance().displayImage(, imgGift, ShipFoodUtils.options(R.drawable.img_flash));
        try {
//            tvName.setText(data.get(position).itemName);
            String price = data.get(position).itemPrice;
            tvPrice.setText(CommonUtils.stringVietNamCurrency(Double.parseDouble(data.get(position).itemPrice)));
            Log.d("akn", "getViewaaaa: " + data.get(position).itemImageUrl.imageUrl);
            Glide.with(this.activity).load(data.get(position).itemImageUrl.imageUrl).into(imgAvatar);
        } catch (Exception e) {
        }
        return convertView;
    }

}
