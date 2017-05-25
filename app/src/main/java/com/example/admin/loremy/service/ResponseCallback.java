package com.example.admin.loremy.service;

import org.json.JSONObject;

/**
 * Created by MyPC on 28/04/2016.
 */
public interface ResponseCallback {
    public void callback(boolean success, JSONObject jsonObject);
}
