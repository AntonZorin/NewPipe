package com.wezom.parts.subs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.wezom.common.adapters.BaseRecyclerViewAdapter;
import com.wezom.common.adapters.BaseViewHolder;
import com.wezom.net.models.Snippet;
import com.wezom.net.models.Subscription;

import org.schabi.newpipe.databinding.ListChannelItemBinding;

public class SubscriptionsAdapter
        extends BaseRecyclerViewAdapter<Subscription, BaseViewHolder<ListChannelItemBinding>> {

    interface Callbacks {
        void onClick(String channelId);
    }

    private Callbacks callbacks;

    @Override
    protected BaseViewHolder<ListChannelItemBinding> createHolder(LayoutInflater inflater,
                                                                  @Nullable ViewGroup parent) {
        ListChannelItemBinding binding = ListChannelItemBinding.inflate(inflater, parent, false);
        return new BaseViewHolder<>(binding);
    }

    @Override
    protected void bindHolder(@NonNull BaseViewHolder<ListChannelItemBinding> holder,
                              int position) {
        Snippet subInfo = items.get(position).snippet;
        holder.binding.getRoot().setOnClickListener(view -> {
            if (callbacks != null) callbacks.onClick(subInfo.resourceId.channelId);
        });
        Picasso.get()
                .load(subInfo.thumbnails.high.url)
                .into(holder.binding.itemThumbnailView);
        holder.binding.itemTitleView.setText(subInfo.title);
        holder.binding.itemChannelDescriptionView.setText(subInfo.description);
//        holder.binding.itemAdditionalDetails.setText();
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }
}
