package com.wezom.ui.home;

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

import org.schabi.newpipe.databinding.FragmentHomeBinding;
import org.schabi.newpipe.util.NavigationHelper;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SharedPreferencesManager shared;
    private YoutubeApiManager api;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareTempDependencies();
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

        HomeListAdapter adapter = new HomeListAdapter();
        adapter.setCallbacks((link, title) -> {
            NavigationHelper.openVideoDetailFragment(getFragmentManager(), 0, link, title);
        });
        binding.homeList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.homeList.setAdapter(adapter);

        disposables.add(api.getHomeFeed(null).subscribe(
                r -> adapter.update(r.items),
                e -> Toast.makeText(requireContext(), "Oops! Something wrong!", Toast.LENGTH_LONG).show()
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