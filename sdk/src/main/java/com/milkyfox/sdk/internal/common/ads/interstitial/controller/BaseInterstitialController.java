/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.interstitial.controller;

import android.content.Context;

import com.milkyfox.sdk.internal.common.ads.BaseController;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.BaseAdData;

public abstract class BaseInterstitialController<T extends BaseAdData> extends BaseController<T> {
    private IInterstitialControllerListener mListener;
    private boolean mLoaded = false;
    private boolean mFailed = false;

    public BaseInterstitialController(T data, IInterstitialControllerListener controllerListener) {
        super(data);
        this.mListener = controllerListener;
    }

    public abstract void show(Context context);

    protected void notifyLoaded() {
        if (!mLoaded && !mFailed) {
            mLoaded = true;
            if (mListener != null) {
                mListener.load(this);
            }
        }
    }

    protected final void notifyFailed(String error) {
        if (!mLoaded && !mFailed) {
            mFailed = true;
            if (mListener != null) {
                mListener.fail(this, error);
            }
        }
    }

    protected void notifyShown() {
        if (!mFailed) {
            if (mListener != null) {
                mListener.show(this);
            }
        }
    }

    protected void notifyClicked() {
        if (!mFailed) {
            if (mListener != null) {
                mListener.click(this);
            }
        }
    }

    protected void notifyClosed() {
        if (!mFailed) {
            if (mListener != null) {
                mListener.close(this);
            }
        }
    }
}
