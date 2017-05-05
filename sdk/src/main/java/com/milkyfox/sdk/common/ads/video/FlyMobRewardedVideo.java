/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.common.ads.video;

import android.app.Activity;

public class FlyMobRewardedVideo {

    public static void initialize(Activity activity, String adUnit) {
        FlyMobRewardedVideoAd.getInstance(activity, adUnit);
    }

    public static void setListener(IMilkyFoxRewardedVideoListener listener) {
        FlyMobRewardedVideoAd.getInstance().mListeners.clear();
        if (listener != null) {
            FlyMobRewardedVideoAd.getInstance().addListener(listener);
        }
    }

    public static boolean isLoaded() {
        FlyMobRewardedVideoAd flyMobRewardedVideoAd = FlyMobRewardedVideoAd.getInstance();
        return flyMobRewardedVideoAd != null && flyMobRewardedVideoAd.isLoaded();
    }

    public static void show() {
        FlyMobRewardedVideoAd.getInstance().show();
    }


}
