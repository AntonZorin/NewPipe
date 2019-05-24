package com.wezom.net;

import android.support.annotation.NonNull;

import com.wezom.utils.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private SharedPreferencesManager shared;

    public TokenInterceptor(SharedPreferencesManager shared) {
        this.shared = shared;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String accessToken = shared.getAccessToken();
        if (accessToken == null) return chain.proceed(chain.request());

        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.addHeader("Accept", "application/json");
        requestBuilder.addHeader("Authorization", "Bearer " + accessToken);

        return chain.proceed(requestBuilder.build());
    }
}
