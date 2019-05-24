package com.wezom.di;

import com.wezom.ui.feed.FeedFragment;
import com.wezom.ui.home.HomeFragment;
import com.wezom.ui.trends.TrendsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, DataModule.class, NetworkModule.class})
@Singleton
public interface RootComponent {
    void inject(HomeFragment fragment);
    void inject(FeedFragment fragment);
    void inject(TrendsFragment fragment);
}
