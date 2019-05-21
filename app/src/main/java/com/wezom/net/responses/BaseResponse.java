package com.wezom.net.responses;

import com.wezom.net.models.Error;
import com.wezom.net.models.PageInfo;

public class BaseResponse {
    public String nextPageToken;
    public String prevPageToken;
    public PageInfo pageInfo;
    public Error error;
}
