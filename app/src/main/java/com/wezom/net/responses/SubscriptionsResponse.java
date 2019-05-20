package com.wezom.net.responses;

import com.google.gson.annotations.SerializedName;
import com.wezom.net.models.PageInfo;
import com.wezom.net.models.Subscription;

import java.util.List;

public class SubscriptionsResponse extends BaseResponse {
    public String nextPageToken;
    public String prevPageToken;
    public PageInfo pageInfo;
    @SerializedName("items") public List<Subscription> subs;
}
