/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.common.ads.video;

import android.app.Activity;
import android.os.Handler;

import com.milkyfox.sdk.common.ads.MilkyFoxAdStatus;
import com.milkyfox.sdk.common.ads.MilkyFoxBaseAd;
import com.milkyfox.sdk.internal.common.ads.video.controller.BaseRewardedVideoController;
import com.milkyfox.sdk.internal.common.ads.video.controller.IRewardedVideoControllerListener;
import com.milkyfox.sdk.internal.common.ads.video.controller.RewardedVideoControllerFactory;
import com.milkyfox.sdk.internal.server.RequestManager;
import com.milkyfox.sdk.internal.server.listeners.BaseRequestListener;
import com.milkyfox.sdk.internal.server.request.impl.data.LoadAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.BaseAdData;
import com.milkyfox.sdk.internal.server.response.impl.ErrorResponse;
import com.milkyfox.sdk.internal.server.response.impl.LoadRewardedVideoSuccessResponse;
import com.milkyfox.sdk.internal.utils.MilkyFoxLog;
import com.milkyfox.sdk.internal.utils.app_lifecycle.AppLifecycleHelper;
import com.milkyfox.sdk.internal.utils.app_lifecycle.IAppLifeCycleListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

class MilkyFoxRewardedVideoAd extends MilkyFoxBaseAd {
    private static MilkyFoxRewardedVideoAd instance;

    private LoadRewardedVideoSuccessResponse mResponse;

    private volatile MilkyFoxAdStatus mLoadingStatus = MilkyFoxAdStatus.IDLE;

    private static final int LOAD_DELAY = 5 * 1000;
    private Handler mHanldler = new Handler();

    private static final Integer TIMER_PERIOD = 3 * 60 * 1000;
    private volatile Timer mTimer;

    private Long mLastLoadTime = null;

    private static final Boolean syncList = false;
    private List<BaseRewardedVideoController> mRewardedVideoControllerList = new ArrayList<BaseRewardedVideoController>();

    private MilkyFoxRewardedVideoAd(Activity activity, String adUnit) {
        super(activity, adUnit);
        runTimer();
        AppLifecycleHelper.addListener(activity, new IAppLifeCycleListener() {
            @Override
            public void start() {
                runTimer();
            }

            @Override
            public void stop() {
                stopTimer();
            }
        });
    }

    public static synchronized MilkyFoxRewardedVideoAd getInstance(Activity activity, String adUnit) {
        if (instance == null || !instance.mAdUnit.equals(adUnit)) {
            instance = new MilkyFoxRewardedVideoAd(activity, adUnit);
        }
        return instance;
    }

    public static synchronized MilkyFoxRewardedVideoAd getInstance() {
        return instance;
    }

    Set<IMilkyFoxRewardedVideoListener> mListeners = new LinkedHashSet<IMilkyFoxRewardedVideoListener>();

    void addListener(IMilkyFoxRewardedVideoListener listener) {
        mListeners.add(listener);
    }

    void removeListener(IMilkyFoxRewardedVideoListener listener) {
        mListeners.remove(listener);
    }

    public void load() {
        if (mLoadingStatus == MilkyFoxAdStatus.IDLE) {
            long curTime = new Date().getTime();
            if (mLastLoadTime == null || curTime - mLastLoadTime > LOAD_DELAY) {
                mLastLoadTime = curTime;
                mLoadingStatus = MilkyFoxAdStatus.LOADING;
                if (mActivity != null) {
                    RequestManager.getInstance(mActivity).loadRewardedVideo(
                            new LoadAdData(mAdUnit),
                            new InnerRequestListener());
                }
            }
        }
    }

    public boolean isLoaded() {
        synchronized (syncList) {
            for (BaseRewardedVideoController rewardedVideoController : mRewardedVideoControllerList) {
                if (rewardedVideoController.isLoaded()) {
                    return true;
                }
            }
        }
        preloadAllControllersIfNeeded();
        return false;
    }

    private void preloadAllControllersIfNeeded() {
        synchronized (syncList) {
            for (BaseRewardedVideoController rewardedVideoController : mRewardedVideoControllerList) {
                if (!rewardedVideoController.isLoaded()) {
                    rewardedVideoController.preload(mActivity);
                }
            }
        }
    }

    public void show() {
        if (mStatus == MilkyFoxAdStatus.IDLE) {
            if (isLoaded()) {
                try {
                    boolean shown = false;
                    synchronized (syncList) {
                        for (BaseRewardedVideoController rewardedVideoController : mRewardedVideoControllerList) {
                            if (rewardedVideoController.isLoaded()) {
                                rewardedVideoController.show(mActivity);
                                MilkyFoxLog.log(String.format("showing %s", rewardedVideoController.getDisplay()));
                                shown = true;
                                mStatus = MilkyFoxAdStatus.SHOWING;
                                break;
                            } else {
                                rewardedVideoController.preload(mActivity);
                            }
                        }
                    }
                    if (!shown) {
                        notifyClosed();
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    MilkyFoxLog.log(ex.getMessage(), true);
                    notifyClosed();
                }
            }
        } else {
            logErrorStatus("show");
        }
    }

    private void notifyLoaded() {
        if (isDestroyed()) {
            return;
        }

        for (IMilkyFoxRewardedVideoListener listener : mListeners) {
            listener.load();
        }
    }

    private void notifyShown(BaseRewardedVideoController rewardedVideoController) {
        if (isDestroyed()) {
            return;
        }
        for (IMilkyFoxRewardedVideoListener listener : mListeners) {
            listener.show();
        }
        mLoadingStatus = MilkyFoxAdStatus.IDLE;

        loadDelayed(LOAD_DELAY);
    }

    private void notifyStarted(BaseRewardedVideoController rewardedVideoController) {
        if (isDestroyed()) {
            return;
        }
        for (IMilkyFoxRewardedVideoListener listener : mListeners) {
            listener.start();
        }
    }

    private void notifyCompleted(BaseRewardedVideoController rewardedVideoController) {
        if (isDestroyed()) {
            return;
        }
        for (IMilkyFoxRewardedVideoListener listener : mListeners) {
            listener.complete();
        }
    }

    private void notifyClosed() {
        if (isDestroyed()) {
            return;
        }
        mStatus = MilkyFoxAdStatus.IDLE;
        for (IMilkyFoxRewardedVideoListener listener : mListeners) {
            listener.close();
        }
    }

    private void loadAds() {
        synchronized (syncList) {
            mRewardedVideoControllerList.clear();
        }
        for (BaseAdData data : mResponse.mList) {
            BaseRewardedVideoController rewardedVideoController = null;
            try {
                rewardedVideoController = RewardedVideoControllerFactory.getController(data, new IRewardedVideoControllerListener() {

                    @Override
                    public void loaded(BaseRewardedVideoController controller) {
                        MilkyFoxLog.log(String.format("loaded %s", controller.getDisplay()));
                        notifyLoaded();
                    }

                    @Override
                    public void shown(BaseRewardedVideoController controller) {
                        notifyShown(controller);
                    }

                    @Override
                    public void closed(BaseRewardedVideoController controller) {
                        notifyClosed();
                    }

                    @Override
                    public void started(BaseRewardedVideoController controller) {
                        notifyStarted(controller);
                    }

                    @Override
                    public void completed(BaseRewardedVideoController controller) {
                        notifyCompleted(controller);
                    }

                    @Override
                    public void failed(BaseRewardedVideoController controller, String error) {
                        MilkyFoxLog.log(String.format("preload failed: %s", error));
                    }
                });
                if (rewardedVideoController != null) {
                    MilkyFoxLog.log(String.format("preload %s", rewardedVideoController.getDisplay()));
                    rewardedVideoController.preload(mActivity);
                    synchronized (syncList) {
                        mRewardedVideoControllerList.add(rewardedVideoController);
                    }
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
                MilkyFoxLog.log(ex.getMessage(), true);
            }
        }
    }

    private void loadDelayed(final int timeout) {
        mHanldler.postDelayed(new Runnable() {
            @Override
            public void run() {
                load();
            }
        }, timeout);
    }

    private class InnerRequestListener extends BaseRequestListener<LoadRewardedVideoSuccessResponse> {

        @Override
        public void success(LoadRewardedVideoSuccessResponse response) {
            if (isDestroyed()) {
                return;
            }
            mLoadingStatus = MilkyFoxAdStatus.IDLE;
            mResponse = response;
            loadAds();
        }

        @Override
        public void error(final ErrorResponse response) {
            mLoadingStatus = MilkyFoxAdStatus.IDLE;
            if (response.mStatusCode == 204) {
                synchronized (syncList) {
                    mRewardedVideoControllerList.clear();
                }
            } else {
                preloadAllControllersIfNeeded();
                loadDelayed(LOAD_DELAY);
            }
        }

        @Override
        public void canceled() {
            mLoadingStatus = MilkyFoxAdStatus.IDLE;
            //nothing to do
        }
    }

    private void runTimer() {
        synchronized (TIMER_PERIOD) {
            if (mTimer == null) {
                mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mHanldler.post(new Runnable() {
                            @Override
                            public void run() {
                                load();
                            }
                        });
                    }
                }, 0, TIMER_PERIOD);
            }
        }
    }

    private void stopTimer() {
        synchronized (TIMER_PERIOD) {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
