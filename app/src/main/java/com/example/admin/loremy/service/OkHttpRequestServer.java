package com.example.admin.loremy.service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by MyPC on 15/07/2016.
 */
public class OkHttpRequestServer {
    ResponseCallback responseCallback;
    Activity activity;
    OkHttpClient client;

    public OkHttpRequestServer(Activity context) {
        this.activity = context;
        this.client = CustomOkHttpClient.getOkHttpClient(context);
    }

    public void RequestServer(String fullUrl, HashMap<String, String> params, ResponseCallback callback) {
        responseCallback = callback;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(fullUrl).newBuilder();
        if (params != null) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                urlBuilder.addQueryParameter(key, params.get(key));
            }
        }
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("silkroad_lang", Locale.getDefault().getLanguage().equals("vi") ? "vi_vn" : "vi_vn")
                .addHeader("token", activity.getSharedPreferences(Constant.name_sharedPreferences, Context.MODE_PRIVATE).getString(Constant.Key_silkroad_token, ""))
                .build();
        doRequest(request);
    }

    public void RequestServerPost(String fullUrl, String json, ResponseCallback callback) {
        responseCallback = callback;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(fullUrl).newBuilder();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("silkroad_lang", Locale.getDefault().getLanguage().equals("vi") ? "vi_vn" : "vi_vn")
                .addHeader("token", activity.getSharedPreferences(Constant.name_sharedPreferences, Context.MODE_PRIVATE).getString(Constant.Key_silkroad_token, ""))
                .post(body)
                .build();
        doRequest(request);
    }

    public void RequestServerMultipartBody(String fullUrl, HashMap<String, String> params, File file, ResponseCallback callback) {
        responseCallback = callback;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(fullUrl).newBuilder();
        if (params != null) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                urlBuilder.addQueryParameter(key, params.get(key));
            }
        }
        String url = urlBuilder.build().toString();
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", UUID.randomUUID().toString(),
                        RequestBody.create(MediaType.parse("image/png"), file))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("silkroad_lang", Locale.getDefault().getLanguage().equals("vi") ? "vi_vn" : "en_us")
                .addHeader("token", activity.getSharedPreferences(Constant.name_sharedPreferences, Context.MODE_PRIVATE).getString(Constant.Key_silkroad_token, ""))
                .post(formBody)
                .build();
        doRequest(request);
    }

    void doRequest(Request request) {
        this.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogUtils.showDialogServerProblem(activity);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showDialogServerProblem(activity);
                        }
                    });
                } else {
                    try {
                        final JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.d("lam", "onResponse: " + jsonObject);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (DialogUtils.checkErrorCode(activity, jsonObject) == false) {
                                    responseCallback.callback(true, jsonObject);
                                } else {
                                    responseCallback.callback(false, jsonObject);
                                }

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
