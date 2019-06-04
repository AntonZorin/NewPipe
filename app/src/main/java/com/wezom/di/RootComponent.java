package com.wezom.di;

import com.wezom.parts.feed.FeedFragment;
import com.wezom.parts.recommendations.RecommendationsFragment;
import com.wezom.parts.subs.SubscriptionsFragment;
import com.wezom.parts.trends.TrendsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, DataModule.class, NetworkModule.class})
@Singleton
public interface RootComponent {
    void inject(RecommendationsFragment fragment);
    void inject(FeedFragment fragment);
    void inject(TrendsFragment fragment);
    void inject(SubscriptionsFragment fragment);
}
