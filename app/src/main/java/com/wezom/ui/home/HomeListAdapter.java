package com.wezom.ui.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.wezom.net.models.HomeItem;

import org.schabi.newpipe.databinding.ListStreamItemBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.Holder> {

    interface Callbacks {
        void openVideo(String link, String title);
    }

    class Holder extends RecyclerView.ViewHolder {

        private ListStreamItemBinding binding;

        Holder(ListStreamItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setup(HomeItem item) {
            Picasso.get().load(item.snippet.thumbnails.high.url).into(binding.itemThumbnailView);
            binding.itemUploaderView.setText(item.snippet.channelTitle);
            binding.itemVideoTitleView.setText(item.snippet.title);
            binding.getRoot().setOnClickListener(v -> {
                if (callbacks == null) return;
                callbacks.openVideo(item.getVideoLink(), item.snippet.title);
            });
        }
    }

    private ArrayList<HomeItem> items = new ArrayList<>();
    private Callbacks callbacks;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListStreamItemBinding binding = ListStreamItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setup(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    public void update(List<HomeItem> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}
