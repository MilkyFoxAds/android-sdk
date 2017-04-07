/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.common.ads;

import android.app.Activity;

import com.milkyfox.sdk.internal.common.ads.BaseController;
import com.milkyfox.sdk.internal.server.response.impl.BaseLoadAdSuccessResponse;

public abstract class MilkyFoxBaseMediationAd<T extends BaseController, K extends BaseLoadAdSuccessResponse> extends MilkyFoxBaseAd {
    protected static final int TIMEOUT_INTERVAL = 15 * 1000;
    protected T mCurController;
    protected K mResponse;
    protected int mId;

    public MilkyFoxBaseMediationAd(Activity activity, String adUnit) {
        super(activity, adUnit);
    }

    protected boolean hasNextAd() {
        return (mId + 1) < mResponse.mList.size();
    }
}
