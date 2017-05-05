/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.interstitial.video.controller.impl;

import android.app.Activity;
import android.content.Context;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.adcolony.sdk.AdColonyZone;
import com.milkyfox.sdk.internal.common.ads.interstitial.video.controller.BaseRewardedVideoController;
import com.milkyfox.sdk.internal.common.ads.interstitial.video.controller.IRewardedVideoControllerListener;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.AdcolonyRewardedVideoAdData;

public class AdcolonyRewardedVideoController extends BaseRewardedVideoController<AdcolonyRewardedVideoAdData> {
    private AdColonyRewardListener mAdColonyRewardListener;
    private AdColonyInterstitialListener mAdColonyInterstitialListener;
    private AdColonyInterstitial mAd;

    public AdcolonyRewardedVideoController(AdcolonyRewardedVideoAdData mData, IRewardedVideoControllerListener listener) {
        super(mData, listener);
    }

    @Override
    public boolean isLoaded() {
        return mAd != null && !mAd.isExpired();
    }

    @Override
    public void preload(Activity activity) {

        if (!isActivityImplemented(activity, "com.adcolony.sdk.AdColonyInterstitialActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.adcolony.sdk.AdColonyAdViewActivity")) {
            return;
        }


        AdColonyAppOptions appOptions = new AdColonyAppOptions();

        AdColony.configure(activity, appOptions, mData.mAppId, mData.mZoneId);

        mAdColonyRewardListener = new AdColonyRewardListener() {
            @Override
            public void onReward(AdColonyReward adColonyReward) {
                if (adColonyReward.success()) {
                    notifyCompleted();
                }
                notifyClosed();
            }
        };

        mAdColonyInterstitialListener = new AdColonyInterstitialListener() {
            /** Ad passed back in request filled callback, ad can now be shown */
            @Override
            public void onRequestFilled(AdColonyInterstitial ad) {
                mAd = ad;
                notifyLoaded();
            }

            /** Ad request was not filled */
            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                notifyFailed("no_fill");
            }

            /** Ad opened, reset UI to reflect state change */
            @Override
            public void onOpened(AdColonyInterstitial ad) {
                notifyShown();
                notifyStarted();
            }

            /** Request a new ad if ad is expiring */
            @Override
            public void onExpiring(AdColonyInterstitial ad) {
            }
        };

        AdColonyAdOptions adColonyAdOptions = new AdColonyAdOptions()
                .enableConfirmationDialog(false)
                .enableResultsDialog(false);


        AdColony.setRewardListener(mAdColonyRewardListener);

        AdColony.requestInterstitial(mData.mZoneId, mAdColonyInterstitialListener, adColonyAdOptions);
    }

    @Override
    public void show(Context context) {
        if (isLoaded()) {
            mAd.show();
        } else {
            notifyClosed();
        }
    }

    @Override
    public void onDestroy() {
    }
}
