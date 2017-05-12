/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.video.controller;

public interface IRewardedVideoControllerListener {
    void load(BaseRewardedVideoController rewardedVideoController);

    void fail(BaseRewardedVideoController rewardedVideoController, String error);

    void show(BaseRewardedVideoController rewardedVideoController);

    void close(BaseRewardedVideoController rewardedVideoController);

    void start(BaseRewardedVideoController rewardedVideoController);

    void complete(BaseRewardedVideoController rewardedVideoController);
}
