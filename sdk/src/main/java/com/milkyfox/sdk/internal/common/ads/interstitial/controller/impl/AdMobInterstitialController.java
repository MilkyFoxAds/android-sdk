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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.BaseInterstitialController;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.IInterstitialControllerListener;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.AdMobInterstitialAdData;

import java.util.Locale;

public class AdMobInterstitialController extends BaseInterstitialController<AdMobInterstitialAdData> {

    private InterstitialAd mInterstitialAd;

    public AdMobInterstitialController(AdMobInterstitialAdData mData, IInterstitialControllerListener listener) {
        super(mData, listener);
    }

    @Override
    public void preload(Activity activity) {
        if (!isActivityImplemented(activity, "com.google.android.gms.ads.AdActivity")) {
            return;
        }

        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(mData.mAdUnit);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                notifyClosed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                notifyFailed(String.format(Locale.US, "error code %d", errorCode));
            }

            @Override
            public void onAdLeftApplication() {
                notifyClicked();
            }

            @Override
            public void onAdOpened() {
                notifyShown();
            }

            @Override
            public void onAdLoaded() {
                notifyLoaded();
            }
        });

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void show(Context context) {
        mInterstitialAd.show();
    }

    @Override
    public void onDestroy() {
    }
}
