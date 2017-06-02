/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.interstitial.controller;

import com.milkyfox.sdk.internal.common.ads.interstitial.controller.impl.AdMobInterstitialController;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.impl.AppLovinInterstitialController;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.impl.FacebookInterstitialController;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.impl.MoPubInterstitialController;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.impl.StartAppInterstitialController;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.BaseAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.AdMobInterstitialAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.AppLovinInterstitialAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.FacebookInterstitialAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.MoPubInterstitialAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.StartAppInterstitialAdData;

public class InterstitialControllerFactory {
    public static BaseInterstitialController getController(BaseAdData adData, IInterstitialControllerListener listener) {
        if (adData instanceof AdMobInterstitialAdData) {
            return new AdMobInterstitialController((AdMobInterstitialAdData) adData, listener);
        } else if (adData instanceof FacebookInterstitialAdData) {
            return new FacebookInterstitialController((FacebookInterstitialAdData) adData, listener);
        } else if (adData instanceof AppLovinInterstitialAdData) {
            return new AppLovinInterstitialController((AppLovinInterstitialAdData) adData, listener);
        } else if (adData instanceof MoPubInterstitialAdData) {
            return new MoPubInterstitialController((MoPubInterstitialAdData) adData, listener);
        } else if (adData instanceof StartAppInterstitialAdData) {
            return new StartAppInterstitialController((StartAppInterstitialAdData) adData, listener);
        }
        return null;
    }
}
