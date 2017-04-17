/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.interstitial.controller;

import com.milkyfox.sdk.internal.common.ads.interstitial.controller.impl.FacebookInterstitialController;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.BaseAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.FacebookInterstitialAdData;

public class InterstitialControllerFactory {
    public static BaseInterstitialController getController(BaseAdData adData, IInterstitialControllerListener listener) {
//        if (adData instanceof AdMobInterstitialAdData) {
//            return new AdMobInterstitialController((AdMobInterstitialAdData) adData, listener);
//        }else
        if (adData instanceof FacebookInterstitialAdData) {
            return new FacebookInterstitialController((FacebookInterstitialAdData) adData, listener);
        }
        return null;
    }
}
