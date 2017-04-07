/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.common.ads.interstitial;


public interface IMilkyFoxInterstitialListener {
    void load(MilkyFoxInterstitial interstitial);

    void fail(MilkyFoxInterstitial interstitial);

    void show(MilkyFoxInterstitial interstitial);

    void click(MilkyFoxInterstitial interstitial);

    void close(MilkyFoxInterstitial interstitial);
}
