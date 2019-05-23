package com.wezom.net.models;

import org.schabi.newpipe.util.Constants;

public class TrendVideo implements Video {

    public String id;
    public Snippet snippet;

    @Override
    public String getVideoName() {
        return snippet.title;
    }

    @Override
    public String getChannelName() {
        return snippet.channelTitle;
    }

    @Override
    public String getVideoLink() {
        return Constants.YOUTUBE_VIDEO_LINK + id;
    }

    @Override
    public String getLogoLink() {
        return snippet.thumbnails.high.url;
    }
}
