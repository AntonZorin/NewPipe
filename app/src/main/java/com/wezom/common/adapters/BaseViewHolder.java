package com.wezom.common.adapters;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public class BaseViewHolder<BINDING extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public BINDING binding;

    public BaseViewHolder(@NonNull BINDING binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
