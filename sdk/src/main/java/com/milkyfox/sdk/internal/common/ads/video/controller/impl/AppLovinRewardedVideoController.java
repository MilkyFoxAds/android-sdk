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
import android.text.TextUtils;

import com.applovin.adview.AppLovinIncentivizedInterstitial;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdRewardListener;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkSettings;
import com.milkyfox.sdk.internal.common.ads.video.controller.BaseRewardedVideoController;
import com.milkyfox.sdk.internal.common.ads.video.controller.IRewardedVideoControllerListener;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.AppLovinRewardedVideoAdData;

import java.util.Map;

public class AppLovinRewardedVideoController extends BaseRewardedVideoController<AppLovinRewardedVideoAdData> {
    private AppLovinIncentivizedInterstitial mAppLovinRewardedVideo;

    public AppLovinRewardedVideoController(AppLovinRewardedVideoAdData mData, IRewardedVideoControllerListener listener) {
        super(mData, listener);
    }

    @Override
    public boolean isLoaded() {
        return mAppLovinRewardedVideo != null && mAppLovinRewardedVideo.isAdReadyToDisplay();
    }

    @Override
    public void preload(Activity activity) {
        if (!isActivityImplemented(activity, "com.applovin.adview.AppLovinInterstitialActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.applovin.adview.AppLovinConfirmationActivity")) {
            return;
        }
        if (activity instanceof Activity) {
            AppLovinSdkSettings settings = new AppLovinSdkSettings();
            final AppLovinSdk sdk = AppLovinSdk.getInstance(mData.mAdUnit, settings, activity.getApplicationContext());

            mAppLovinRewardedVideo = AppLovinIncentivizedInterstitial.create(sdk);
            mAppLovinRewardedVideo.preload(new AppLovinAdLoadListener() {
                @Override
                public void adReceived(AppLovinAd appLovinAd) {
                    notifyLoaded();
                }

                @SuppressLint("DefaultLocale")
                @Override
                public void failedToReceiveAd(int i) {
                    notifyFailed(String.format("fail: %d", i));
                }
            });
        } else {
            notifyFailed("Your context is not Activity");
        }
    }

    @Override
    public void show(Context context) {
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (isLoaded()) {
                // An ad is ready.  Display the ad with one of the available show methods.

                AppLovinAdRewardListener appLovinAdRewardListener = new AppLovinAdRewardListener() {
                    @Override
                    public void userRewardVerified(AppLovinAd appLovinAd, Map map) {
                    }

                    @Override
                    public void userOverQuota(AppLovinAd appLovinAd, Map map) {
                        notifyFailed("userOverQuota:");
                    }

                    @Override
                    public void userRewardRejected(AppLovinAd appLovinAd, Map map) {
                        notifyClosed();
                    }

                    @Override
                    public void validationRequestFailed(AppLovinAd appLovinAd, int i) {
//                        notifyFailed(String.format("fail: %d", i));
                    }

                    @Override
                    public void userDeclinedToViewAd(AppLovinAd appLovinAd) {
                        notifyClosed();
                    }
                };
                AppLovinAdVideoPlaybackListener appLovinAdVideoPlaybackListener = new AppLovinAdVideoPlaybackListener() {
                    @Override
                    public void videoPlaybackBegan(AppLovinAd appLovinAd) {
                        notifyStarted();
                    }

                    @Override
                    public void videoPlaybackEnded(AppLovinAd appLovinAd, double v, boolean b) {
                        if (b) {
                            notifyCompleted();
                        } else {
                            notifyClosed();
                        }
                    }
                };

                AppLovinAdDisplayListener appLovinAdDisplayListener = new AppLovinAdDisplayListener() {
                    @Override
                    public void adDisplayed(AppLovinAd appLovinAd) {
                        notifyShown();
                    }

                    @Override
                    public void adHidden(AppLovinAd appLovinAd) {
                        notifyClosed();
                    }
                };

                AppLovinAdClickListener appLovinAdClickListener = new AppLovinAdClickListener() {
                    @Override
                    public void adClicked(AppLovinAd appLovinAd) {
                    }
                };

                if (TextUtils.isEmpty(mData.mPlacement)) {
                    mAppLovinRewardedVideo.show(activity, appLovinAdRewardListener, appLovinAdVideoPlaybackListener, appLovinAdDisplayListener, appLovinAdClickListener);
                } else {
                    mAppLovinRewardedVideo.show(activity, mData.mPlacement, appLovinAdRewardListener, appLovinAdVideoPlaybackListener, appLovinAdDisplayListener, appLovinAdClickListener);
                }
            } else {
                notifyFailed("No rewarded ad is currently available.");
            }
        } else {
            notifyFailed("Your context is not Activity");
        }
    }

    @Override
    public void onDestroy() {
    }
}
