package com.wezom.di;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.wezom.net.TokenInterceptor;
import com.wezom.net.YoutubeApiManager;
import com.wezom.net.YoutubeApiService;
import com.wezom.utils.SharedPreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(SharedPreferencesManager shared) {
        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor(message -> Log.d("network", message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        TokenInterceptor tokenInterceptor = new TokenInterceptor(shared);

        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(tokenInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public YoutubeApiService provideYoutubeApiService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build()
                .create(YoutubeApiService.class);
    }

    @Provides
    @Singleton
    public YoutubeApiManager provideYoutubeApiManager(YoutubeApiService service,
                                                      SharedPreferencesManager shared) {
        return new YoutubeApiManager(service, shared);
    }
}
