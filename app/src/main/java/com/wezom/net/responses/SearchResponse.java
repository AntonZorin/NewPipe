package com.wezom.net.responses;

import com.google.gson.annotations.SerializedName;
import com.wezom.net.models.FoundedVideo;

import java.util.List;

public class SearchResponse extends BaseResponse {
    @SerializedName("items") public List<FoundedVideo> videos;
}
