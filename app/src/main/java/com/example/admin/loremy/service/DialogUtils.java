package com.example.admin.loremy.service;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 5/15/2017.
 */

class DialogUtils
{
    public static void showDialogServerProblem(Activity activity) {
        try {
            CommonUtils.showOkDialog(activity, "Đã xảy ra lỗi, Vui lòng thử lại sau",
                    null);
        } catch (Exception e) {
        }
    }

    public static boolean checkErrorCode(Activity activity, JSONObject jsonObject) {
        try {
            if (jsonObject.getInt("error") != 0) {
                CommonUtils.showOkDialog(activity, jsonObject.getString("message"), null);
                return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
