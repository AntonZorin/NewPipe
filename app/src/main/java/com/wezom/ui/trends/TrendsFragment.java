package com.wezom.ui.trends;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.wezom.net.YoutubeApiManager;
import com.wezom.net.YoutubeApiService;
import com.wezom.utils.SharedPreferencesManager;

import org.schabi.newpipe.databinding.FragmentTrendsBinding;
import org.schabi.newpipe.util.NavigationHelper;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrendsFragment extends Fragment {

    private FragmentTrendsBinding binding;
    private TrendsAdapter adapter = new TrendsAdapter();
    private SharedPreferencesManager shared;
    private YoutubeApiManager api;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareTempDependencies();
        adapter.setCallbacks((link, title) ->
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
        binding.trendsRecyclerView.setAdapter(adapter);
        binding.trendsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        disposables.add(api.getTrends(null).subscribe(
                r -> adapter.update(r.videos),
                e -> Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        ));
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
}
