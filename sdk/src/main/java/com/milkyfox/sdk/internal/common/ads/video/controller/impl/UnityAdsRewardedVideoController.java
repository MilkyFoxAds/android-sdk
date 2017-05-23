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

import com.milkyfox.sdk.internal.common.ads.video.controller.BaseRewardedVideoController;
import com.milkyfox.sdk.internal.common.ads.video.controller.IRewardedVideoControllerListener;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.UnityAdsRewardedVideoAdData;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

import java.lang.ref.WeakReference;

public class UnityAdsRewardedVideoController extends BaseRewardedVideoController<UnityAdsRewardedVideoAdData> {

    private static UnityAdsListener unityAdsListener = new UnityAdsListener();

    private static class UnityAdsListener implements IUnityAdsListener {
        WeakReference<Activity> mWeakActivity;
        UnityAdsRewardedVideoController mController;

        public void setActivity(Activity mActivity) {
            this.mWeakActivity = new WeakReference<Activity>(mActivity);
        }

        public void setController(UnityAdsRewardedVideoController controller) {
            this.mController = controller;
        }

        @Override
        public void onUnityAdsReady(final String zoneId) {
            if (mWeakActivity != null) {
                Activity activity = mWeakActivity.get();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mController.mData.mAdUnit.equals(zoneId)) {
                                mController.notifyLoaded();
                            }
                        }
                    });
                }
            }
        }

        @Override
        public void onUnityAdsStart(final String zoneId) {
            if (mWeakActivity != null) {
                Activity activity = mWeakActivity.get();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mController.mData.mAdUnit.equals(zoneId)) {
                                mController.notifyShown();
                                mController.notifyStarted();
                            }
                        }
                    });
                }
            }
        }

        @Override
        public void onUnityAdsFinish(final String zoneId, final UnityAds.FinishState finishState) {
            if (mWeakActivity != null) {
                Activity activity = mWeakActivity.get();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mController.mData.mAdUnit.equals(zoneId)) {
                                if (UnityAds.FinishState.COMPLETED == finishState) {
                                    mController.notifyCompleted();
                                    mController.notifyClosed();
                                } else {
                                    mController.notifyClosed();
                                }
                            }
                        }
                    });
                }
            }
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, final String zoneId) {
            if (mWeakActivity != null) {
                Activity activity = mWeakActivity.get();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mController.mData.mAdUnit.equals(zoneId)) {
                                mController.notifyFailed(zoneId);
                            }
                        }
                    });
                }
            }
        }
    }


    public UnityAdsRewardedVideoController(UnityAdsRewardedVideoAdData mData, IRewardedVideoControllerListener listener) {
        super(mData, listener);
    }

    @Override
    public boolean isLoaded() {
        return UnityAds.isReady(mData.mAdUnit);
    }

    @Override
    public void preload(Activity activity) {
        if (!isActivityImplemented(activity, "com.unity3d.ads.adunit.AdUnitActivity")) {
            return;
        }
        if (!isActivityImplemented(activity, "com.unity3d.ads.adunit.AdUnitSoftwareActivity")) {
            return;
        }
        unityAdsListener.setActivity(activity);
        unityAdsListener.setController(this);
        UnityAds.initialize(activity, mData.mGameId, unityAdsListener);

        if (UnityAds.isReady(mData.mAdUnit)) {
            notifyLoaded();
        }
    }

    @Override
    public void show(Context context) {
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            unityAdsListener.setActivity(activity);
            unityAdsListener.setController(this);
            UnityAds.show(activity, mData.mAdUnit);
        } else {
            notifyFailed("Your context is not Activity");
        }
    }

    @Override
    public void onDestroy() {
    }

}
