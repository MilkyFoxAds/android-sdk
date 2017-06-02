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
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.StartAppInterstitialAdData;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

public class StartAppInterstitialController extends BaseInterstitialController<StartAppInterstitialAdData> {

    StartAppAd mInterstitial;

    public StartAppInterstitialController(StartAppInterstitialAdData mData, IInterstitialControllerListener listener) {
        super(mData, listener);
    }

    @Override
    public void preload(Activity activity) {
        if (!isActivityImplemented(activity, "com.startapp.android.publish.ads.list3d.List3DActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.startapp.android.publish.adsCommon.activities.OverlayActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.startapp.android.publish.adsCommon.activities.FullScreenActivity")) {
            return;
        }
        if (!isServiceImplemented(activity, "com.startapp.android.publish.common.metaData.PeriodicMetaDataService")) {
            return;
        }
        if (!isServiceImplemented(activity, "com.startapp.android.publish.common.metaData.InfoEventService")) {
            return;
        }

        StartAppSDK.init(activity, mData.mAdUnit, false);

        StartAppSDK.enableReturnAds(false);

        mInterstitial = new StartAppAd(activity);
        mInterstitial.loadAd(new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                notifyLoaded();
            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
                notifyFailed(ad == null ? null : ad.getErrorMessage());
            }
        });
    }

    @Override
    public void show(Context context) {
        if (mInterstitial != null && mInterstitial.isReady()) {
            mInterstitial.showAd(new AdDisplayListener() {
                @Override
                public void adHidden(Ad ad) {
                    notifyClosed();
                }

                @Override
                public void adDisplayed(Ad ad) {
                    notifyShown();
                }

                @Override
                public void adClicked(Ad ad) {
                    notifyClicked();
                }

                @Override
                public void adNotDisplayed(Ad ad) {
                    notifyFailed("not displayed");
                }
            });
        }
    }

    @Override
    public void onDestroy() {
    }
}
