/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.interstitial.controller;

public interface IInterstitialControllerListener {
    void load(BaseInterstitialController controller);

    void fail(BaseInterstitialController controller, String error);

    void show(BaseInterstitialController controller);

    void click(BaseInterstitialController controller);

    void close(BaseInterstitialController controller);
}
