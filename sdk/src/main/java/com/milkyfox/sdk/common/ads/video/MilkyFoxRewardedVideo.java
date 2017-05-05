/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.common.ads.video;

import android.app.Activity;

public class MilkyFoxRewardedVideo {

    public static void initialize(Activity activity, String adUnit) {
        MilkyFoxRewardedVideoAd.getInstance(activity, adUnit);
    }

    public static void setListener(IMilkyFoxRewardedVideoListener listener) {
        MilkyFoxRewardedVideoAd.getInstance().mListeners.clear();
        if (listener != null) {
            MilkyFoxRewardedVideoAd.getInstance().addListener(listener);
        }
    }

    public static boolean isLoaded() {
        MilkyFoxRewardedVideoAd milkyFoxRewardedVideoAd = MilkyFoxRewardedVideoAd.getInstance();
        return milkyFoxRewardedVideoAd != null && milkyFoxRewardedVideoAd.isLoaded();
    }

    public static void show() {
        MilkyFoxRewardedVideoAd.getInstance().show();
    }


}
