/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.video.controller;

public interface IRewardedVideoControllerListener {
    void loaded(BaseRewardedVideoController rewardedVideoController);

    void failed(BaseRewardedVideoController rewardedVideoController, String error);

    void shown(BaseRewardedVideoController rewardedVideoController);

    void closed(BaseRewardedVideoController rewardedVideoController);

    void started(BaseRewardedVideoController rewardedVideoController);

    void completed(BaseRewardedVideoController rewardedVideoController);
}
