package com.wezom.net.responses;

import com.google.gson.annotations.SerializedName;
import com.wezom.net.models.PageInfo;
import com.wezom.net.models.Video;

import java.util.List;

public class SearchResponse {
    public String nextPageToken;
    public String prevPageToken;
    public PageInfo pageInfo;
    @SerializedName("items") public List<Video> videos;
}
