package com.wezom.net.models;

import static org.schabi.newpipe.util.Constants.YOUTUBE_VIDEO_LINK;

public class TrendVideo {
    public String id;
    public Snippet snippet;

    public String getLink() {
        return YOUTUBE_VIDEO_LINK + id;
    }
}
