package com.wezom.net.models;

import static org.schabi.newpipe.util.Constants.YOUTUBE_VIDEO_LINK;

public class HomeItem {
    public Snippet snippet;
    public ContentDetails contentDetails;

    public String getVideoLink() {
        return YOUTUBE_VIDEO_LINK + contentDetails.upload.videoId;
    }
}
