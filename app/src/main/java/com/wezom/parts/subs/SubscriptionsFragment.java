package com.wezom.parts.subs;

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
import org.schabi.newpipe.databinding.FragmentSubscriptionsBinding;
import org.schabi.newpipe.util.NavigationHelper;

import javax.inject.Inject;

public class SubscriptionsFragment extends KiviBaseFragment {

    @Inject YoutubeApiManager api;

    private FragmentSubscriptionsBinding binding;
    private SubscriptionsAdapter adapter = new SubscriptionsAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApp().getRootComponent().inject(this);
        adapter.setCallbacks(channelId ->
                NavigationHelper.openMyFeedFragment(getFragmentManager(), channelId));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSubscriptionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view, savedInstanceState);
        binding.itemsList.setAdapter(adapter);
        binding.itemsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        fetchSubs();
    }

    private void fetchSubs() {
        showLoading();
        disposables.add(api.getSubscriptions(null).subscribe(
                response -> {
                    adapter.fullUpdate(response.subs);
                    hideLoading();
                },
                e -> {
                    Log.e("error", e.getMessage());
                    Toast.makeText(requireContext(), "Shit happens", Toast.LENGTH_SHORT).show();
                    hideLoading();
                }
        ));
    }
}
