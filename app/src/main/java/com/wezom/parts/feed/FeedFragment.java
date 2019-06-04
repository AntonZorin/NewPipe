package com.wezom.parts.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wezom.common.fragments.KiviBaseFragment;
import com.wezom.net.YoutubeApiManager;

import org.schabi.newpipe.App;
import org.schabi.newpipe.databinding.FragmentFeedBinding;
import org.schabi.newpipe.util.NavigationHelper;

import javax.inject.Inject;

public class FeedFragment extends KiviBaseFragment {

    @Inject YoutubeApiManager api;

    private static final String KEY_CHANEL_ID = "KEY_CHANEL_ID";

    private FragmentFeedBinding binding;
    private FeedAdapter adapter = new FeedAdapter();
    private String chanelID;

    public static FeedFragment newInstance(String id) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(KEY_CHANEL_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApp().getRootComponent().inject(this);
        chanelID = getArguments() != null ? getArguments().getString(KEY_CHANEL_ID) : null;
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
//        fetchSubs();
        fetchVideos();
    }

//    private void fetchSubs() {
//        showLoading();
//        disposables.add(api.getSubscriptions(null).subscribe(response -> {
//            String[] ids = new String[response.subs.size()];
//            for (int i = 0; i < response.subs.size(); i++) {
//                ids[i] = response.subs.get(i).snippet.resourceId.channelId;
//            }
//            fetchVideos(ids);
//        }, e -> Log.e("error", e.getMessage())));
//    }

//    private void fetchVideos(String[] channelIds) {
//        disposables.add(
//            Observable.fromArray(channelIds)
//                    .flatMap(id -> api.searchVideos(id, null).toObservable())
//                    .subscribe(
//                            response -> adapter.addNewItems(response.videos),
//                            e -> {
//                                showEmptyState();
//                                Log.e("error", e.getMessage());
//                            },
//                            () -> {
//                                adapter.refresh();
//                                hideLoading();
//                            }
//                    )
//        );
//    }

    private void fetchVideos() {
        showLoading();
        disposables.add(api.searchVideos(chanelID, null).subscribe(
                r -> {
                    hideLoading();
                    adapter.fullUpdate(r.videos);
                },
                e -> {
                    hideLoading();
                    showEmptyState();
                    Toast.makeText(getContext(), "Error occurred!", Toast.LENGTH_SHORT).show();
                    Log.e("error", e.getMessage());
                }
        ));
    }
}
