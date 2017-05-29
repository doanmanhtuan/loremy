package com.example.admin.loremy.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.admin.loremy.adapter.MenuAdapter;
import com.example.admin.loremy.data.MenuData;
import com.example.admin.loremy.R;
import com.example.admin.loremy.service.OkHttpRequestServer;
import com.example.admin.loremy.service.ResponseCallback;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Admin on 23/5/2017.
 */

public class Activity_Menu extends AppCompatActivity {
    private static final String TAG = "TUAN";
    ArrayList<MenuData> menuArray;
    MenuAdapter menuAdapter;
    @Bind(R.id.gvMeNu)
    GridView gvMeNu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        this.setTitle("Menu");
        menuArray = new ArrayList<>();
        menuAdapter = new MenuAdapter(this, 0, menuArray);
        gvMeNu.setAdapter(menuAdapter);
        gvMeNu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity_Menu.this, Activity_Detail_Menu.class);
                Bundle bundle = new Bundle();
                bundle.putString("price", menuAdapter.getItem(position).itemPrice);
                bundle.putString("imgurl", menuArray.get(position).itemImageUrl.imageUrl);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        getMenu();

    }

    public void getMenu() {
        String url = "http://103.200.20.217:8080/libra/api/product/index";
        new OkHttpRequestServer(this).RequestServer(url, null, new ResponseCallback() {
            @Override
            public void callback(boolean success, JSONObject jsonObject) {
                Log.d(TAG, "called ok " + jsonObject);
                if (!success) return;

                Gson gson = new Gson();
                menuArray.clear();
                try {
                    JSONArray jsonArrGift = jsonObject.getJSONObject("data").getJSONArray("items_products");
                    for (int i = 0; i < jsonArrGift.length(); i++) {
                        MenuData sanpham = gson.fromJson(jsonArrGift.get(i).toString(), MenuData.class);
                        menuArray.add(sanpham);
                    }

                    Log.d(TAG, "doc xong " + jsonArrGift.length() + " phan tu");
                    menuAdapter.notifyDataSetChanged();
                } catch (Exception xp) {

                }
            }
        });

        return;
    }
}
