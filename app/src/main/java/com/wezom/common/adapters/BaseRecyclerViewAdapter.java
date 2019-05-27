package com.wezom.common.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<ITEM, VH extends BaseViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected ArrayList<ITEM> items = new ArrayList<>();

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup root, int type) {
        LayoutInflater inflater = LayoutInflater.from(root.getContext());
        return createHolder(inflater, root);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        bindHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected abstract VH createHolder(LayoutInflater inflater, @Nullable ViewGroup parent);

    protected abstract void bindHolder(@NonNull VH holder, int position);

    public void fullUpdate(List<ITEM> newItems) {
        items.clear();
        addNewItems(newItems);
        refresh();
    }

    public void addNewItems(List<ITEM> newItems) {
        items.addAll(newItems);
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}
