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


public class MilkyFoxInterstitialUnityBridge {

    private Activity mActivity;
    private MilkyFoxInterstitial mMilkyFoxInterstitial;

    private Runnable mRunnableLoad = null;
    private Runnable mRunnableFail = null;
    private Runnable mRunnableShow = null;
    private Runnable mRunnableClick = null;
    private Runnable mRunnableClose = null;

    public MilkyFoxInterstitialUnityBridge(final Activity activity, final String adUnit) {
        mActivity = activity;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mMilkyFoxInterstitial = new MilkyFoxInterstitial(activity, adUnit);

                mMilkyFoxInterstitial.setListener(new IMilkyFoxInterstitialListener() {
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
                mMilkyFoxInterstitial.show();
            }
        });
    }

    public boolean isLoaded() {
        return mMilkyFoxInterstitial.isLoaded();
    }

    public void load() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMilkyFoxInterstitial.load();
            }
        });
    }

    public void onDestroy() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMilkyFoxInterstitial.onDestroy();
            }
        });
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
