package com.example.admin.loremy.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 25/5/2017.
 */

public class MenuData {

    @SerializedName("autoId")
    public String autoId;
    @SerializedName("itemPrice")
    public String itemPrice;
    @SerializedName("itemRichText")
    public String itemRichText;
    @SerializedName("itemImageUrl")
    public ItemImageUrl itemImageUrl;
    @SerializedName("itemName")
    public String itemName;
    @SerializedName("internalMeta")
    public String internalMeta;

}
