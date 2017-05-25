package com.example.admin.loremy.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 25/5/2017.
 */

public class ItemImageUrl {
    @SerializedName("imageWidth")
    public int imageWidth;
    @SerializedName("imageHeight")
    public int imageHeight;
    @SerializedName("imageUrl")
    public String imageUrl;
    @SerializedName("error")
    public String error;
}
