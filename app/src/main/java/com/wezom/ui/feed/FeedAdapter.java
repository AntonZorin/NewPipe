package com.wezom.ui.feed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.wezom.net.models.FoundedVideo;

import org.schabi.newpipe.databinding.ListStreamItemBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private ArrayList<FoundedVideo> videos = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {

        private ListStreamItemBinding binding;

        ViewHolder(ListStreamItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setup(FoundedVideo video) {
            Picasso.get().load(video.snippet.thumbnails.high.url).into(binding.itemThumbnailView);
            binding.itemUploaderView.setText(video.snippet.channelTitle);
            binding.itemVideoTitleView.setText(video.snippet.title);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        ListStreamItemBinding binding = ListStreamItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setup(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    // example 2018-02-20T11:25:57.000Z
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.ROOT);

    void add(List<FoundedVideo> nextPart) {
        videos.addAll(nextPart);
    }

    void update() {
        // sort by date
        Collections.sort(videos, (first, second) -> {
            String rawDate1 = first.snippet.publishedAt;
            String rawDate2 = second.snippet.publishedAt;
//            Log.d("update", rawDate1 + " " + rawDate2);
            try {
                Date date1 = formatter.parse(rawDate1);
                Date date2 = formatter.parse(rawDate2);
                boolean answer = date1.getTime() < date2.getTime();
                Log.d("update", String.format("%d %d %b", date1.getTime(), date2.getTime(), answer));
                return answer ? 1 : -1;
            } catch (ParseException e) {
                Log.e("update", e.getMessage());
            }
            return 0;
        });
        notifyDataSetChanged();
    }
}
