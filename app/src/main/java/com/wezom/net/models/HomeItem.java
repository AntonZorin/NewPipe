package com.wezom.net.models;

import org.schabi.newpipe.util.Constants;

public class HomeItem implements Video {

    public Snippet snippet;
    public ContentDetails contentDetails;

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
        return Constants.YOUTUBE_VIDEO_LINK + contentDetails.upload.videoId;
    }

    @Override
    public String getLogoLink() {
        return snippet.thumbnails.high.url;
    }
}
