package com.wezom.net;

import com.wezom.net.responses.HomeResponse;
import com.wezom.net.responses.PlaylistsResponse;
import com.wezom.net.responses.RefreshedTokenResponse;
import com.wezom.net.responses.SearchResponse;
import com.wezom.net.responses.SubscriptionsResponse;
import com.wezom.net.responses.TrendingVideosResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static org.schabi.newpipe.util.Constants.TOKEN_ENDPOINT;

public interface YoutubeApiService {

    @POST(TOKEN_ENDPOINT)
    Single<RefreshedTokenResponse> refreshToken(
            @Query("refresh_token") String refreshToken,
            @Query("client_id") String clientId,
            @Query("grant_type") String grantType
    );

    @GET("subscriptions")
    Single<SubscriptionsResponse> getSubscriptions(
            @Header("Authorization") String token,
            @Query("part") String part,
            @Query("mine") boolean mine,
            @Query("maxResults") int maxResults,
            @Query("pageToken") String pageToken
    );

    @GET("videos")
    Single<TrendingVideosResponse> getTrendingVideos(
            @Header("Authorization") String token,
            @Query("part") String part,
            @Query("chart") String chart,
            @Query("regionCode") String regionCode,
            @Query("maxResults") int maxResults,
            @Query("pageToken") String pageToken
    );

    @GET("playlists")
    Single<PlaylistsResponse> getPlaylists(
            @Header("Authorization") String token,
            @Query("part") String part,
            @Query("channelId") String channelId,
            @Query("maxResults") int maxResults,
            @Query("pageToken") String pageToken
    );

    @GET("search")
    Single<SearchResponse> doSearch(
            @Header("Authorization") String token,
            @Query("part") String part,
            @Query("channelId") String channelId,
            @Query("order") String order,
            @Query("maxResults") int maxResults,
            @Query("pageToken") String pageToken
    );

    @GET("activities")
    Single<HomeResponse> getHomeFeed(
            @Header("Authorization") String token,
            @Query("part") String part,
            @Query("home") boolean home,
            @Query("regionCode") String regionCode,
            @Query("maxResults") int maxResults,
            @Query("pageToken") String pageToken
    );
}
