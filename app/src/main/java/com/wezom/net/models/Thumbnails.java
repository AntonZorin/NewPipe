package com.wezom.net.models;

import com.google.gson.annotations.SerializedName;

public class Thumbnails {
    @SerializedName("default") public Thumbnail small;
    @SerializedName("medium") public Thumbnail medium;
    @SerializedName("high") public Thumbnail high;
}
