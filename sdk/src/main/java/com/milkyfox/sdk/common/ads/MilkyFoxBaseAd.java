/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.common.ads;

import android.app.Activity;

import com.milkyfox.sdk.internal.utils.MilkyFoxLog;

public abstract class MilkyFoxBaseAd {
    protected Activity mActivity;
    protected String mAdUnit;
    protected volatile MilkyFoxAdStatus mStatus = MilkyFoxAdStatus.IDLE;

    public MilkyFoxBaseAd(Activity activity, String adUnit) {
        mActivity = activity;
        if (activity == null) {
            MilkyFoxLog.log("context is null!", true);
        }
        this.mAdUnit = adUnit;
    }

    public boolean isDestroyed() {
        return mStatus == MilkyFoxAdStatus.DESTROYED;
    }

    protected void logErrorStatus(String action) {
        MilkyFoxLog.log(String.format(action + ": Ad is %s, ignoring.", mStatus.name()), true);
    }

    public void onDestroy() {
        mStatus = MilkyFoxAdStatus.DESTROYED;
        mActivity = null;
    }
}
