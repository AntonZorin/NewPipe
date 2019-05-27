package com.wezom.common.fragments;

import org.schabi.newpipe.fragments.BaseStateFragment;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Extends {@link BaseStateFragment} cause it contains progress bar functionality.
 * I'm too lazy to implement it by myself.
 */
public abstract class KiviBaseFragment extends BaseStateFragment {

    protected CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }
}
