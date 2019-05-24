package com.wezom.net;

import android.util.Log;

import com.wezom.net.responses.BaseResponse;
import com.wezom.net.responses.HomeResponse;
import com.wezom.net.responses.PlaylistsResponse;
import com.wezom.net.responses.SearchResponse;
import com.wezom.net.responses.SubscriptionsResponse;
import com.wezom.net.responses.TrendingVideosResponse;
import com.wezom.utils.Rx;
import com.wezom.utils.SharedPreferencesManager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

import static org.schabi.newpipe.util.Constants.OAUTH_CLIENT_ID;

public class YoutubeApiManager {

    private YoutubeApiService youtube;
    private SharedPreferencesManager shared;

    public YoutubeApiManager(YoutubeApiService service, SharedPreferencesManager spm) {
        youtube = service;
        shared = spm;
    }

    /**
     * Checks if it's time to update token. If so, returns single, that send refresh request, saves
     * updated data and maps itself to target request, that we got in parameter.
     */
    private <T extends BaseResponse> Single<T> withFreshToken(Single<T> targetRequest) {
        // check if token still valid
        long diff = shared.getTokenExpTime() - System.currentTimeMillis();
        if (diff > 0) {
            long minLeft = TimeUnit.MILLISECONDS.toMinutes(diff);
            Log.d("network", String.format("no need to update token (%d minutes left)", minLeft));
            return targetRequest.compose(Rx.backgroundTransformer());
        }
        // update token and save new one
        Log.d("network", "time to update token");
        return youtube
                .refreshToken(shared.getRefreshToken(), OAUTH_CLIENT_ID, "refresh_token")
                .flatMap(fresh -> {
                    shared.setAccessToken(fresh.accessToken);
                    shared.setTokenExpTime(fresh.getTokenLifetimeInMillis());
                    return targetRequest;
                })
                .compose(Rx.backgroundTransformer());
    }

    public Single<SubscriptionsResponse> getSubscriptions(String pageToken) {
        return withFreshToken(youtube.getSubscriptions("snippet", true, 50, pageToken));
    }

    public Single<TrendingVideosResponse> getTrends(String pageToken) {
        return withFreshToken(youtube
                .getTrendingVideos("snippet,contentDetails", "mostPopular", "UA", 50, pageToken));
    }

    public Single<PlaylistsResponse> getPlaylists(String channelId, String pageToken) {
        return withFreshToken(youtube.getPlaylists("snippet", channelId, 50, pageToken));
    }

    public Single<SearchResponse> searchVideos(String channelId, String nextPageToken) {
        return withFreshToken(youtube.doSearch("snippet,id", channelId, "date", 50, nextPageToken));
    }

    public Single<HomeResponse> getHomeFeed(String nextPageToken) {
        return withFreshToken(
                youtube.getHomeFeed("snippet,contentDetails", true, "UA", 50, nextPageToken));
    }
}
