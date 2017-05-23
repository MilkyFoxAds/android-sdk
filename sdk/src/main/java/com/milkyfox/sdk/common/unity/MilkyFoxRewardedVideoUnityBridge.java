/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.common.unity;

import android.app.Activity;

import com.milkyfox.sdk.common.ads.video.IMilkyFoxRewardedVideoListener;
import com.milkyfox.sdk.common.ads.video.MilkyFoxRewardedVideo;

public class MilkyFoxRewardedVideoUnityBridge {
    private Activity mActivity;
    private Runnable mRunnableLoad = null;
    private Runnable mRunnableShow = null;
    private Runnable mRunnableClose = null;
    private Runnable mRunnableStart = null;
    private Runnable mRunnableComplete = null;

    public void initialize(final Activity activity, final String adUnit) {
        mActivity = activity;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MilkyFoxRewardedVideo.initialize(activity, adUnit);
                MilkyFoxRewardedVideo.setListener(new IMilkyFoxRewardedVideoListener() {

                    @Override
                    public void load() {
                        if (mRunnableLoad != null) {
                            mRunnableLoad.run();
                        }
                    }

                    @Override
                    public void show() {
                        if (mRunnableShow != null) {
                            mRunnableShow.run();
                        }
                    }

                    @Override
                    public void close() {
                        if (mRunnableClose != null) {
                            mRunnableClose.run();
                        }
                    }

                    @Override
                    public void start() {
                        if (mRunnableStart != null) {
                            mRunnableStart.run();
                        }
                    }

                    @Override
                    public void complete() {
                        if (mRunnableComplete != null) {
                            mRunnableComplete.run();
                        }
                    }
                });
            }
        });

    }

    public void show() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MilkyFoxRewardedVideo.show();
            }
        });
    }

    public boolean isLoaded() {
        return MilkyFoxRewardedVideo.isLoaded();
    }

    public void setLoadedListener(Runnable runnable) {
        mRunnableLoad = runnable;
    }

    public void setShownListener(Runnable runnable) {
        mRunnableShow = runnable;
    }

    public void setStartedListener(Runnable runnable) {
        mRunnableStart = runnable;
    }

    public void setCompletedListener(Runnable runnable) {
        mRunnableComplete = runnable;
    }

    public void setClosedListener(Runnable runnable) {
        mRunnableClose = runnable;
    }
}
