/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.interstitial.controller.impl;

import android.app.Activity;
import android.content.Context;

import com.milkyfox.sdk.internal.common.ads.interstitial.controller.BaseInterstitialController;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.IInterstitialControllerListener;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.MoPubInterstitialAdData;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

import java.util.Locale;

public class MoPubInterstitialController extends BaseInterstitialController<MoPubInterstitialAdData> {

    MoPubInterstitial mInterstitial;

    public MoPubInterstitialController(MoPubInterstitialAdData mData, IInterstitialControllerListener listener) {
        super(mData, listener);
    }

    @Override
    public void preload(Activity activity) {
        if (!isActivityImplemented(activity, "com.mopub.mobileads.MoPubActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.mopub.mobileads.MraidActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.mopub.common.MoPubBrowser")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.mopub.mobileads.MraidVideoPlayerActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.mopub.mobileads.RewardedMraidActivity")) {
            return;
        }

        mInterstitial = new MoPubInterstitial(activity, mData.mAdUnit);
        mInterstitial.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(MoPubInterstitial moPubInterstitial) {
                notifyLoaded();
            }

            @Override
            public void onInterstitialFailed(MoPubInterstitial moPubInterstitial, MoPubErrorCode moPubErrorCode) {
                notifyFailed(String.format(Locale.US, "error code %s", moPubErrorCode == null ? "null" : moPubErrorCode.toString()));
            }

            @Override
            public void onInterstitialShown(MoPubInterstitial moPubInterstitial) {
                notifyShown();
            }

            @Override
            public void onInterstitialClicked(MoPubInterstitial moPubInterstitial) {
                notifyClicked();
            }

            @Override
            public void onInterstitialDismissed(MoPubInterstitial moPubInterstitial) {
                notifyClosed();
            }
        });
        mInterstitial.load();
    }

    @Override
    public void show(Context context) {
        if (mInterstitial != null) {
            mInterstitial.show();
        }
    }

    @Override
    public void onDestroy() {
        if (mInterstitial != null) {
            mInterstitial.destroy();
            mInterstitial = null;
        }
    }
}
