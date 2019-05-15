package com.wezom.net;

import com.wezom.net.responses.RefreshedTokenResponse;
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

    public Single<SubscriptionsResponse> getSubscriptions() {
        String token = "Bearer " + shared.getAccessToken();
        return youtube.getSubscriptions(token, "snippet", true, 50)
                .compose(Rx.applyBackgroundScheduler());
    }

    public Single<TrendingVideosResponse> getTrends() {
        String token = "Bearer " + shared.getAccessToken();
        return youtube.getTrendingVideos(token, "snippet,contentDetails", "mostPopular", "UA", 50)
                .compose(Rx.applyBackgroundScheduler());
    }
}
