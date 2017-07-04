/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.common.unity;

import android.app.Activity;

import com.milkyfox.sdk.common.ads.interstitial.IMilkyFoxInterstitialListener;
import com.milkyfox.sdk.common.ads.interstitial.MilkyFoxInterstitial;
import com.milkyfox.sdk.common.ads.interstitial.MilkyFoxInterstitialStaticAdapter;

public class MilkyFoxInterstitialStaticAdapterUnityBridge {
    private Activity mActivity;
    private Runnable mRunnableLoad = null;
    private Runnable mRunnableFail = null;
    private Runnable mRunnableShow = null;
    private Runnable mRunnableClick = null;
    private Runnable mRunnableClose = null;

    public void initialize(final Activity activity, final String adUnit) {
        mActivity = activity;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MilkyFoxInterstitialStaticAdapter.initialize(activity, adUnit);
                MilkyFoxInterstitialStaticAdapter.setListener(new IMilkyFoxInterstitialListener() {
                    @Override
                    public void load(MilkyFoxInterstitial interstitial) {
                        if (mRunnableLoad != null) {
                            mRunnableLoad.run();
                        }
                    }

                    @Override
                    public void fail(MilkyFoxInterstitial interstitial) {
                        if (mRunnableFail != null) {
                            mRunnableFail.run();
                        }
                    }

                    @Override
                    public void show(MilkyFoxInterstitial interstitial) {
                        if (mRunnableShow != null) {
                            mRunnableShow.run();
                        }
                    }

                    @Override
                    public void click(MilkyFoxInterstitial interstitial) {
                        if (mRunnableClick != null) {
                            mRunnableClick.run();
                        }
                    }

                    @Override
                    public void close(MilkyFoxInterstitial interstitial) {
                        if (mRunnableClose != null) {
                            mRunnableClose.run();
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
                MilkyFoxInterstitialStaticAdapter.show();
            }
        });
    }

    public boolean isLoaded() {
        return MilkyFoxInterstitialStaticAdapter.isLoaded();
    }

    public void setLoadListener(Runnable runnable) {
        mRunnableLoad = runnable;
    }

    public void setFailListener(Runnable runnable) {
        mRunnableFail = runnable;
    }

    public void setShowListener(Runnable runnable) {
        mRunnableShow = runnable;
    }

    public void setClickListener(Runnable runnable) {
        mRunnableClick = runnable;
    }

    public void setCloseListener(Runnable runnable) {
        mRunnableClose = runnable;
    }
}
