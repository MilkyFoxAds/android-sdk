/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.video.controller.impl;

import android.annotation.SuppressLint;
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

    private static RewardedVideoAd mAd = null;

    @SuppressLint("StaticFieldLeak")
    private static Activity mActivity = null;

    private static volatile boolean isLoaded = false;

    public AdMobRewardedVideoController(AdMobRewardedVideoAdData mData, IRewardedVideoControllerListener listener) {
        super(mData, listener);
    }

    @Override
    public boolean isLoaded() {
        if (mAd != null && mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mAd != null) {
                        isLoaded = mAd.isLoaded();
                    }
                }
            });
        }
        return mAd != null && isLoaded;
    }

    @Override
    public void preload(Activity activity) {
        if (!isActivityImplemented(activity, "com.google.android.gms.ads.AdActivity")) {
            return;
        }
        mActivity = activity;

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mAd == null) {
                    mAd = MobileAds.getRewardedVideoAdInstance(mActivity);
                }
                mAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
                    @Override
                    public void onRewardedVideoAdLoaded() {
                        isLoaded = true;
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
        });


    }

    @Override
    public void show(Context context) {
        if (mAd != null && mAd.isLoaded()) {
            mAd.show();
        } else {
            notifyClosed();
        }
    }

    @Override
    public void onDestroy() {
        mAd = null;
        mActivity = null;
    }
}
