package com.wezom.common.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.wezom.net.models.Video;

import org.schabi.newpipe.databinding.ListStreamItemBinding;

public abstract class VideosRecyclerViewAdapter<VIDEO extends Video>
        extends BaseRecyclerViewAdapter<VIDEO, BaseViewHolder<ListStreamItemBinding>> {

    public interface Callbacks {

        /**
         * @param title Title of video.
         * @param link URL link to this video.
         */
        void onVideoClicked(String title, String link);
    }

    protected Callbacks callbacks;

    @Override
    protected BaseViewHolder<ListStreamItemBinding> createHolder(LayoutInflater inflater,
                                                                 @Nullable ViewGroup parent) {
        ListStreamItemBinding binding = ListStreamItemBinding.inflate(inflater, parent, false);
        return new BaseViewHolder<>(binding);
    }

    @Override
    protected void bindHolder(@NonNull BaseViewHolder<ListStreamItemBinding> holder, int position) {
        VIDEO video = items.get(position);
        holder.binding.getRoot().setOnClickListener(v -> {
            if (callbacks != null) {
                callbacks.onVideoClicked(video.getVideoName(), video.getVideoLink());
            }
        });
        Picasso.get()
                .load(video.getLogoLink())
                .into(holder.binding.itemThumbnailView);
        holder.binding.itemUploaderView.setText(video.getChannelName());
        holder.binding.itemVideoTitleView.setText(video.getVideoName());
        holder.binding.itemDurationView.setVisibility(View.INVISIBLE);
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }
}