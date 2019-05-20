package com.wezom.net;

import com.wezom.net.responses.PlaylistsResponse;
import com.wezom.net.responses.RefreshedTokenResponse;
import com.wezom.net.responses.SearchResponse;
import com.wezom.net.responses.SubscriptionsResponse;
import com.wezom.net.responses.TrendingVideosResponse;
import com.wezom.utils.Rx;
import com.wezom.utils.SharedPreferencesManager;

import io.reactivex.Single;

import static org.schabi.newpipe.util.Constants.OAUTH_CLIENT_ID;

public class YoutubeApiManager {

    private YoutubeApiService youtube;
    private SharedPreferencesManager shared;

    public YoutubeApiManager(YoutubeApiService service, SharedPreferencesManager spm) {
        youtube = service;
        shared = spm;
    }

    public Single<RefreshedTokenResponse> refreshToken() {
        String refreshToken = shared.getRefreshToken();
        return youtube.refreshToken(refreshToken, OAUTH_CLIENT_ID, "")
                .compose(Rx.applyBackgroundScheduler());
    }

    public Single<SubscriptionsResponse> getSubscriptions(String pageToken) {
        String token = "Bearer " + shared.getAccessToken();
        return youtube.getSubscriptions(token, "snippet", true, 50, pageToken)
                .compose(Rx.applyBackgroundScheduler());
    }

    public Single<TrendingVideosResponse> getTrends(String pageToken) {
        String token = "Bearer " + shared.getAccessToken();
        return youtube.getTrendingVideos(token, "snippet,contentDetails", "mostPopular", "UA", 50, pageToken)
                .compose(Rx.applyBackgroundScheduler());
    }

    public Single<PlaylistsResponse> getPlaylists(String channelId, String pageToken) {
        String token = "Bearer " + shared.getAccessToken();
        return youtube.getPlaylists(token, "snippet", channelId, 50, pageToken)
                .compose(Rx.applyBackgroundScheduler());
    }

    public Single<SearchResponse> searchVideos(String channelId, String nextPageToken) {
        String token = "Bearer " + shared.getAccessToken();
        return youtube.doSearch(token, "snippet,id", channelId, "date", 50, nextPageToken)
                .compose(Rx.applyBackgroundScheduler());
    }
}
