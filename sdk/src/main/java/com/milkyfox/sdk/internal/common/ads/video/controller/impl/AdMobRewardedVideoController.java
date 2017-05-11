/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.video.controller.impl;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.milkyfox.sdk.internal.common.ads.video.controller.BaseRewardedVideoController;
import com.milkyfox.sdk.internal.common.ads.video.controller.IRewardedVideoControllerListener;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.AdMobRewardedVideoAdData;


public class AdMobRewardedVideoController extends BaseRewardedVideoController<AdMobRewardedVideoAdData> {

    private RewardedVideoAd mAd;

    public AdMobRewardedVideoController(AdMobRewardedVideoAdData mData, IRewardedVideoControllerListener listener) {
        super(mData, listener);
    }

    @Override
    public boolean isLoaded() {
        return mAd != null && mAd.isLoaded();
    }

    @Override
    public void preload(Activity activity) {
        if (!isActivityImplemented(activity, "com.google.android.gms.ads.AdActivity")) {
            return;
        }

        mAd = MobileAds.getRewardedVideoAdInstance(activity);
        mAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                notifyLoaded();
            }

            @Override
            public void onRewardedVideoAdOpened() {
                notifyShown();
            }

            @Override
            public void onRewardedVideoStarted() {
                notifyStarted();
            }

            @Override
            public void onRewardedVideoAdClosed() {
                notifyClosed();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                notifyCompleted();
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                notifyFailed(String.valueOf(i));
            }
        });

        mAd.loadAd(mData.mAdUnit, new AdRequest.Builder().build());
    }

    @Override
    public void show(Context context) {
        if (mAd.isLoaded()) {
            mAd.show();
        } else {
            notifyClosed();
        }
    }

    @Override
    public void onDestroy() {

    }
}
