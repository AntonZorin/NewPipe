package com.wezom.di;

import android.content.Context;

import com.wezom.utils.SharedPreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    public SharedPreferencesManager provideSharedPreferencesManager(Context context) {
        return new SharedPreferencesManager(context);
    }
}
