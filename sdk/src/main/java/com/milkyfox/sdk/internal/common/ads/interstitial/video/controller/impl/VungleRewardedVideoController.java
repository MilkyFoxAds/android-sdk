/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads.interstitial.video.controller.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.milkyfox.sdk.internal.common.ads.interstitial.video.controller.BaseRewardedVideoController;
import com.milkyfox.sdk.internal.common.ads.interstitial.video.controller.IRewardedVideoControllerListener;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.VungleRewardedVideoAdData;
import com.vungle.publisher.EventListener;
import com.vungle.publisher.VunglePub;


public class VungleRewardedVideoController extends BaseRewardedVideoController<VungleRewardedVideoAdData> {

    private VunglePub mVunglePub;

    public VungleRewardedVideoController(VungleRewardedVideoAdData mData, IRewardedVideoControllerListener listener) {
        super(mData, listener);
    }

    @Override
    public boolean isLoaded() {
        return mVunglePub != null && mVunglePub.isAdPlayable();
    }

    @Override
    public void preload(Activity activity) {
        final Handler handler = new Handler();

        if (!isActivityImplemented(activity, "com.vungle.publisher.VideoFullScreenAdActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.vungle.publisher.MraidFullScreenAdActivity")) {
            return;
        }

        mVunglePub = VunglePub.getInstance();
        mVunglePub.init(activity, mData.mAdUnit);
        mVunglePub.onResume();

        mVunglePub.setEventListeners(new EventListener() {
            @Override
            public void onAdEnd(final boolean wasSuccessfulView, boolean wasCallToActionClicked) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (wasSuccessfulView) {
                            notifyCompleted();
                        }
                        notifyClosed();
                    }
                });
            }

            @Override
            public void onAdStart() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyShown();
                        notifyStarted();
                    }
                });
            }

            @Override
            public void onAdUnavailable(final String s) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyFailed(s);
                    }
                });
            }

            @Override
            public void onAdPlayableChanged(final boolean isAdPlayable) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isAdPlayable) {
                            notifyLoaded();
                        } else {
                            notifyFailed("NO FILL");
                        }
                    }
                });

            }

            @Override
            public void onVideoView(boolean b, int i, int i1) {
                //deprecated
            }
        });
    }

    @Override
    public void show(Context context) {
        if (mVunglePub.isAdPlayable()) {
            mVunglePub.playAd();
        } else {
            notifyClosed();
        }
    }

    @Override
    public void onDestroy() {
        mVunglePub.onPause();
    }
}
