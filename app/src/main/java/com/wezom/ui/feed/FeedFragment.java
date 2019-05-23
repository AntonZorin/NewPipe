package com.wezom.ui.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.GsonBuilder;
import com.wezom.net.YoutubeApiManager;
import com.wezom.net.YoutubeApiService;
import com.wezom.utils.SharedPreferencesManager;

import org.schabi.newpipe.databinding.FragmentFeedBinding;
import org.schabi.newpipe.util.NavigationHelper;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedFragment extends Fragment {

    private FragmentFeedBinding binding;
    private FeedAdapter adapter = new FeedAdapter();
    private SharedPreferencesManager shared;
    private YoutubeApiManager api;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareTempDependencies();
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
        binding.itemsList.setAdapter(adapter);
        binding.itemsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        fetchSubs();
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }

    private void prepareTempDependencies() { // TODO: remove this shit, use di
        shared = new SharedPreferencesManager(requireContext());
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Log.d("network", message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .build();
        YoutubeApiService service = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build()
                .create(YoutubeApiService.class);
        api = new YoutubeApiManager(service, shared);
    }

    private void fetchSubs() {
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
                            e -> Log.e("error", e.getMessage()),
                            () -> adapter.refreshRecyclerView()
                    )
        );
    }
}
