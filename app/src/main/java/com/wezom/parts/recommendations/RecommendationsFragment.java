package com.wezom.parts.recommendations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wezom.common.fragments.KiviBaseFragment;
import com.wezom.net.YoutubeApiManager;

import org.schabi.newpipe.App;
import org.schabi.newpipe.databinding.FragmentHomeBinding;
import org.schabi.newpipe.util.NavigationHelper;

import javax.inject.Inject;

public class RecommendationsFragment extends KiviBaseFragment {

    @Inject YoutubeApiManager api;

    private FragmentHomeBinding binding;
    private RecommendationsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApp().getRootComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecommendationsAdapter();
        adapter.setCallbacks((title, link) ->
                NavigationHelper.openVideoDetailFragment(getFragmentManager(), 0, link, title));
        binding.homeList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.homeList.setAdapter(adapter);
        fetchData();
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }

    private void fetchData() {
        showLoading();
        disposables.add(api.getHomeFeed(null).subscribe(
                r -> {
                    hideLoading();
                    adapter.fullUpdate(r.items);
                },
                e -> {
                    hideLoading();
                    Toast.makeText(requireContext(), "Oops! Something wrong!", Toast.LENGTH_LONG).show();
                }
        ));
    }
}
