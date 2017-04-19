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
import android.text.TextUtils;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkSettings;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.BaseInterstitialController;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.IInterstitialControllerListener;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.AppLovinInterstitialAdData;

import java.util.Locale;

public class AppLovinInterstitialController extends BaseInterstitialController<AppLovinInterstitialAdData> {
    private AppLovinAd mAppLovinAd;
    private AppLovinInterstitialAdDialog mInterstitialAdDialog;

    public AppLovinInterstitialController(AppLovinInterstitialAdData mData, IInterstitialControllerListener listener) {
        super(mData, listener);
    }

    @Override
    public void preload(final Activity activity) {
        if (!isActivityImplemented(activity, "com.applovin.adview.AppLovinInterstitialActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.applovin.adview.AppLovinConfirmationActivity")) {
            return;
        }

        AppLovinSdkSettings settings = new AppLovinSdkSettings();
        final AppLovinSdk sdk = AppLovinSdk.getInstance(mData.mAdUnit, settings, activity.getApplicationContext());

        mInterstitialAdDialog = AppLovinInterstitialAd.create(sdk, activity);

        mInterstitialAdDialog.setAdDisplayListener(new AppLovinAdDisplayListener() {
            @Override
            public void adDisplayed(AppLovinAd appLovinAd) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyShown();
                    }
                });
            }

            @Override
            public void adHidden(AppLovinAd appLovinAd) {
                notifyClosed();
            }
        });

        mInterstitialAdDialog.setAdClickListener(new AppLovinAdClickListener() {
            @Override
            public void adClicked(AppLovinAd appLovinAd) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyClicked();
                    }
                });
            }
        });

        sdk.getAdService().loadNextAd(AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener() {
            @Override
            public void adReceived(AppLovinAd ad) {
                mAppLovinAd = ad;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyLoaded();
                    }
                });
            }

            @Override
            public void failedToReceiveAd(final int errorCode) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyFailed(String.format(Locale.US, "error code %d", errorCode));
                    }
                });
            }
        });

    }

    @Override
    public void show(Context context) {
        if (TextUtils.isEmpty(mData.mPlacement)) {
            mInterstitialAdDialog.showAndRender(mAppLovinAd);
        } else {
            mInterstitialAdDialog.showAndRender(mAppLovinAd, mData.mPlacement);
        }
    }

    @Override
    public String getName() {
        return "applovin";
    }

    @Override
    public void onDestroy() {
    }
}
