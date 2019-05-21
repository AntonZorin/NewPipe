package com.wezom.net.responses;

import com.google.gson.annotations.SerializedName;
import com.wezom.net.models.TrendVideo;

import java.util.List;

public class TrendingVideosResponse extends BaseResponse {
    @SerializedName("items") public List<TrendVideo> videos;
}
