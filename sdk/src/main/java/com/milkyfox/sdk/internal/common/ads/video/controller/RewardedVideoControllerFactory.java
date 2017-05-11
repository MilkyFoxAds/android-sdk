/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.video.controller;

import com.milkyfox.sdk.internal.common.ads.video.controller.impl.AdMobRewardedVideoController;
import com.milkyfox.sdk.internal.common.ads.video.controller.impl.AdcolonyRewardedVideoController;
import com.milkyfox.sdk.internal.common.ads.video.controller.impl.AppLovinRewardedVideoController;
import com.milkyfox.sdk.internal.common.ads.video.controller.impl.UnityAdsRewardedVideoController;
import com.milkyfox.sdk.internal.common.ads.video.controller.impl.VungleRewardedVideoController;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.BaseAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.AdMobRewardedVideoAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.AdcolonyRewardedVideoAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.AppLovinRewardedVideoAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.UnityAdsRewardedVideoAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.VungleRewardedVideoAdData;

public class RewardedVideoControllerFactory {
    public static BaseRewardedVideoController getController(BaseAdData adData, IRewardedVideoControllerListener listener) {
        if (adData instanceof AdMobRewardedVideoAdData) {
            return new AdMobRewardedVideoController((AdMobRewardedVideoAdData) adData, listener);
        }
        if (adData instanceof AppLovinRewardedVideoAdData) {
            return new AppLovinRewardedVideoController((AppLovinRewardedVideoAdData) adData, listener);
        }
        if (adData instanceof UnityAdsRewardedVideoAdData) {
            return new UnityAdsRewardedVideoController((UnityAdsRewardedVideoAdData) adData, listener);
        }
        if (adData instanceof AdcolonyRewardedVideoAdData) {
            return new AdcolonyRewardedVideoController((AdcolonyRewardedVideoAdData) adData, listener);
        }
        if (adData instanceof VungleRewardedVideoAdData) {
            return new VungleRewardedVideoController((VungleRewardedVideoAdData) adData, listener);
        }
        return null;
    }
}
