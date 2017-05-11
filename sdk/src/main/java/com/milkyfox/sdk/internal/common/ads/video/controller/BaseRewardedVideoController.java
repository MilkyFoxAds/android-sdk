/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.video.controller;

import android.content.Context;

import com.milkyfox.sdk.internal.common.ads.BaseController;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.BaseAdData;

public abstract class BaseRewardedVideoController<T extends BaseAdData> extends BaseController<T> {
    private IRewardedVideoControllerListener mListener;

    public BaseRewardedVideoController(T data, IRewardedVideoControllerListener controllerListener) {
        super(data);
        this.mListener = controllerListener;
    }

    public abstract boolean isLoaded();

    public abstract void show(Context context);

    public BaseAdData getData() {
        return mData;
    }

    protected void notifyLoaded() {
        if (mListener != null) {
            mListener.loaded(this);
        }
    }

    protected final void notifyFailed(String error) {
        if (mListener != null) {
            mListener.failed(this, error);
        }
    }

    protected void notifyShown() {
        if (mListener != null) {
            mListener.shown(this);
        }
    }

    protected void notifyClosed() {
        if (mListener != null) {
            mListener.closed(this);
        }
    }

    protected void notifyStarted() {
        if (mListener != null) {
            mListener.started(this);
        }
    }

    protected void notifyCompleted() {
        if (mListener != null) {
            mListener.completed(this);
        }
    }
}
