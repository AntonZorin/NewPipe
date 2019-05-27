package com.wezom.parts.trends;

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
import org.schabi.newpipe.databinding.FragmentTrendsBinding;
import org.schabi.newpipe.util.NavigationHelper;

import javax.inject.Inject;

public class TrendsFragment extends KiviBaseFragment {

    @Inject YoutubeApiManager api;

    private FragmentTrendsBinding binding;
    private TrendsAdapter adapter = new TrendsAdapter();

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
        binding = FragmentTrendsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view, savedInstanceState);
        binding.trendsRecyclerView.setAdapter(adapter);
        binding.trendsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fetchTrends();
    }

    private void fetchTrends() {
        showLoading();
        disposables.add(api.getTrends(null).subscribe(
                r -> {
                    adapter.fullUpdate(r.videos);
                    hideLoading();
                },
                e -> {
                    hideLoading();
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                }
        ));
    }
}
