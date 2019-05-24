package com.wezom.ui.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wezom.common.fragments.KiviBaseFragment;
import com.wezom.net.YoutubeApiManager;

import org.schabi.newpipe.App;
import org.schabi.newpipe.databinding.FragmentFeedBinding;
import org.schabi.newpipe.util.NavigationHelper;

import javax.inject.Inject;

import io.reactivex.Observable;

public class FeedFragment extends KiviBaseFragment {

    @Inject YoutubeApiManager api;

    private FragmentFeedBinding binding;
    private FeedAdapter adapter = new FeedAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApp().getRootComponent().inject(this);
        adapter.setCallbacks((title, link) ->
                NavigationHelper.openVideoDetailFragment(getFragmentManager(), 0, link, title));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFeedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view, savedInstanceState);
        binding.itemsList.setAdapter(adapter);
        binding.itemsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        fetchSubs();
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }

    private void fetchSubs() {
        showLoading();
        disposables.add(api.getSubscriptions(null).subscribe(response -> {
            String[] ids = new String[response.subs.size()];
            for (int i = 0; i < response.subs.size(); i++) {
                ids[i] = response.subs.get(i).snippet.resourceId.channelId;
            }
            fetchVideos(ids);
        }, e -> Log.e("error", e.getMessage())));
    }

    private void fetchVideos(String[] channelIds) {
        disposables.add(
            Observable.fromArray(channelIds)
                    .flatMap(id -> api.searchVideos(id, null).toObservable())
                    .subscribe(
                            response -> adapter.addNewItems(response.videos),
                            e -> {
                                showEmptyState();
                                Log.e("error", e.getMessage());
                            },
                            () -> {
                                adapter.refreshRecyclerView();
                                hideLoading();
                            }
                    )
        );
    }
}
