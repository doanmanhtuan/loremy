package com.example.admin.loremy.common;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Admin on 24/5/2017.
 */

public class Constant {
    public static final String KEY_IS_LOGIN_FACEBOOK = "KEY_IS_LOGIN_FACEBOOK";
    public static final String KEY_USER_NAME = "KEY_USER_NAME";
    public static final String KEY_ALL_URL = "KEY_ALL_URL";
    public static final String KEY_SEND_OBJECT = "KEY_SEND_OBJECT";
    public static final String KEY_ONE_URL = "KEY_ONE_URL";
    public static String name_sharedPreferences ="sharedPreferences_cabine";
    public static String Key_silkroad_token = "Key_silkroad_token";
    public static String KEY_ID_USER = "KEY_ID_USER";
    public static final Uri mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
            "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
    public static final int PICK_FROM_CAMERA = 11;
    public static final int CROP_FROM_CAMERA = 22;
    public static final int PICK_FROM_FILE = 33;
    public static String name_sharedPreferences_order = "name_sharedPreferences_order";
    public static String Key_jsonArrorder = "Key_jsonArrorder";
    public static NumberFormat vietnamFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
}
