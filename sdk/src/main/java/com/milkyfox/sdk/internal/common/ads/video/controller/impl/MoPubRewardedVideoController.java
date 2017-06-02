/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.video.controller.impl;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.milkyfox.sdk.internal.common.ads.video.controller.BaseRewardedVideoController;
import com.milkyfox.sdk.internal.common.ads.video.controller.IRewardedVideoControllerListener;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.MoPubRewardedVideoAdData;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;

import java.util.Set;


public class MoPubRewardedVideoController extends BaseRewardedVideoController<MoPubRewardedVideoAdData> {

    public MoPubRewardedVideoController(MoPubRewardedVideoAdData mData, IRewardedVideoControllerListener listener) {
        super(mData, listener);
    }

    @Override
    public boolean isLoaded() {
        return MoPubRewardedVideos.hasRewardedVideo(mData.mAdUnit);
    }

    @Override
    public void preload(Activity activity) {
        if (!isActivityImplemented(activity, "com.mopub.mobileads.MoPubActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.mopub.mobileads.MraidActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.mopub.common.MoPubBrowser")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.mopub.mobileads.MraidVideoPlayerActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.mopub.mobileads.RewardedMraidActivity")) {
            return;
        }

        MoPubRewardedVideos.initializeRewardedVideo(activity);

        MoPubRewardedVideos.setRewardedVideoListener(new MoPubRewardedVideoListener() {
            @Override
            public void onRewardedVideoLoadSuccess(@NonNull String s) {
                notifyLoaded();
            }

            @Override
            public void onRewardedVideoLoadFailure(@NonNull String s, @NonNull MoPubErrorCode moPubErrorCode) {
                notifyFailed(moPubErrorCode.toString());
            }

            @Override
            public void onRewardedVideoStarted(@NonNull String s) {
                notifyStarted();
            }

            @Override
            public void onRewardedVideoPlaybackError(@NonNull String s, @NonNull MoPubErrorCode moPubErrorCode) {
                notifyFailed(moPubErrorCode.toString());
            }

            @Override
            public void onRewardedVideoClicked(@NonNull String s) {
            }

            @Override
            public void onRewardedVideoClosed(@NonNull String s) {
                notifyClosed();
            }

            @Override
            public void onRewardedVideoCompleted(@NonNull Set<String> set, @NonNull MoPubReward moPubReward) {
                notifyCompleted();
            }
        });

        MoPubRewardedVideos.loadRewardedVideo(mData.mAdUnit);

    }

    @Override
    public void show(Context context) {
        MoPubRewardedVideos.showRewardedVideo(mData.mAdUnit);
    }

    @Override
    public void onDestroy() {

    }
}
