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
import android.os.Build;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.BaseInterstitialController;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.IInterstitialControllerListener;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.FacebookInterstitialAdData;

public class FacebookInterstitialController extends BaseInterstitialController<FacebookInterstitialAdData> {

    InterstitialAd mInterstitialAd;

    public FacebookInterstitialController(FacebookInterstitialAdData mData, IInterstitialControllerListener listener) {
        super(mData, listener);
    }

    @Override
    public void preload(Activity activity) {
        mInterstitialAd = new InterstitialAd(activity, mData.mAdUnit);
        mInterstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                notifyShown();
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                notifyClosed();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                String message = "EMPTY";
                if (adError != null) {
                    message = adError.getErrorMessage();
                }
                notifyFailed(message);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                notifyLoaded();
            }

            @Override
            public void onAdClicked(Ad ad) {
                notifyClicked();
            }
        });
        mInterstitialAd.loadAd();
    }

    @Override
    public void show(Context context) {
        mInterstitialAd.show();
    }

    @Override
    public String getName() {
        return "facebook";
    }

    @Override
    public void onDestroy() {
        if (mInterstitialAd != null) {
            mInterstitialAd.destroy();
        }
    }
}
