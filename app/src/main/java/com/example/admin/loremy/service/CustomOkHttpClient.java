package com.example.admin.loremy.service;

import android.content.Context;

import okhttp3.OkHttpClient;


/**
 * Created by MyPC on 15/07/2016.
 */
public class CustomOkHttpClient {
    public static OkHttpClient getOkHttpClient(Context context) {
//        SelfSignInClient SelfSignInClient = new SelfSignInClient(context);
      OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        return okHttpClient.build();
    }
}
