package com.wezom.ui.trends;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.wezom.net.models.TrendVideo;

import org.schabi.newpipe.databinding.ListStreamItemBinding;

import java.util.ArrayList;
import java.util.List;

public class TrendsAdapter extends RecyclerView.Adapter<TrendsAdapter.TrendHolder> {

    interface Callbacks {
        void openVideo(String link, String title);
    }

    class TrendHolder extends RecyclerView.ViewHolder {

        private ListStreamItemBinding binding;

        TrendHolder(ListStreamItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setup(TrendVideo video) {
            Picasso.get().load(video.snippet.thumbnails.high.url).into(binding.itemThumbnailView);
            binding.itemUploaderView.setText(video.snippet.channelTitle);
            binding.itemVideoTitleView.setText(video.snippet.title);
            binding.getRoot().setOnClickListener(v -> {
                if (callbacks == null) return;
                callbacks.openVideo(video.getLink(), video.snippet.title);
            });
        }
    }

    private ArrayList<TrendVideo> videos = new ArrayList<>();
    private Callbacks callbacks;

    @NonNull
    @Override
    public TrendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListStreamItemBinding binding = ListStreamItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TrendHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendHolder holder, int position) {
        holder.setup(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    public void update(List<TrendVideo> newVideos) {
        videos.clear();
        videos.addAll(newVideos);
        notifyDataSetChanged();
    }
}
