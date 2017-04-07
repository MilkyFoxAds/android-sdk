/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.common.ads.interstitial;

import android.app.Activity;
import android.os.Handler;

import com.milkyfox.sdk.common.ads.MilkyFoxAdStatus;
import com.milkyfox.sdk.common.ads.MilkyFoxBaseMediationAd;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.BaseInterstitialController;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.IInterstitialControllerListener;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.InterstitialControllerFactory;
import com.milkyfox.sdk.internal.server.RequestManager;
import com.milkyfox.sdk.internal.server.listeners.BaseRequestListener;
import com.milkyfox.sdk.internal.server.request.impl.data.LoadAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.BaseAdData;
import com.milkyfox.sdk.internal.server.response.impl.ErrorResponse;
import com.milkyfox.sdk.internal.server.response.impl.LoadInterstitialSuccessResponse;
import com.milkyfox.sdk.internal.utils.MilkyFoxLog;

import java.util.LinkedHashSet;
import java.util.Set;


public class MilkyFoxInterstitial extends MilkyFoxBaseMediationAd<BaseInterstitialController, LoadInterstitialSuccessResponse> {
    private Set<IMilkyFoxInterstitialListener> mListeners = new LinkedHashSet<IMilkyFoxInterstitialListener>();

    public MilkyFoxInterstitial(Activity activity, String adUnit) {
        super(activity, adUnit);
    }

    public void addListener(IMilkyFoxInterstitialListener listener) {
        mListeners.add(listener);
    }

    public void removeListener(IMilkyFoxInterstitialListener listener) {
        mListeners.remove(listener);
    }

    public void load() {
        if (mStatus == MilkyFoxAdStatus.IDLE) {
            mStatus = MilkyFoxAdStatus.LOADING;
            if (mCurController != null) {
                try {
                    mCurController.onDestroy();
                    mCurController = null;
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    MilkyFoxLog.log(ex.getMessage(), true);
                }
            }
            if (mActivity == null) {
                notifyFailed();
            } else {
                RequestManager.getInstance(mActivity).loadInterstitial(
                        new LoadAdData(mAdUnit),
                        new InnerRequestListener());
            }
        } else {
            if (mStatus == MilkyFoxAdStatus.LOADED) {
                notifyLoaded();
            }
        }
    }

    public boolean isLoaded() {
        return mStatus == MilkyFoxAdStatus.LOADED;
    }

    public void show() {
        if (isLoaded()) {
            try {
                if (mCurController != null && mActivity != null) {
                    MilkyFoxLog.log(String.format("show %s", mCurController.getDisplay()));
                    mCurController.show(mActivity);
                } else {
                    notifyClosed();
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
                MilkyFoxLog.log(ex.getMessage(), true);
                notifyClosed();
            }
        } else {
            logErrorStatus("show");
        }
    }

    private void notifyLoaded() {
        if (isDestroyed()) {
            return;
        }
        if (mStatus == MilkyFoxAdStatus.LOADING || mStatus == MilkyFoxAdStatus.LOADED) {
            if (mCurController != null && mActivity != null) {
                MilkyFoxLog.log(String.format("loaded %s", mCurController.getDisplay()));
            }
            mStatus = MilkyFoxAdStatus.LOADED;
            for (IMilkyFoxInterstitialListener listener : mListeners) {
                listener.load(this);
            }
        }
    }

    private void notifyFailed() {
        if (isDestroyed()) {
            return;
        }
        if (mStatus == MilkyFoxAdStatus.LOADING) {
            mStatus = MilkyFoxAdStatus.IDLE;
            for (IMilkyFoxInterstitialListener listener : mListeners) {
                listener.fail(this);
            }
        }
    }

    private void notifyShown() {
        if (isDestroyed()) {
            return;
        }
        if (mStatus == MilkyFoxAdStatus.LOADED) {
            mStatus = MilkyFoxAdStatus.SHOWING;
            for (IMilkyFoxInterstitialListener listener : mListeners) {
                listener.show(this);
            }
        }
    }

    private void notifyClicked() {
        if (isDestroyed()) {
            return;
        }
        if (mStatus == MilkyFoxAdStatus.SHOWING) {
            for (IMilkyFoxInterstitialListener listener : mListeners) {
                listener.click(this);
            }
        }
    }

    private void notifyClosed() {
        if (isDestroyed()) {
            return;
        }
        if (mStatus == MilkyFoxAdStatus.SHOWING || mStatus == MilkyFoxAdStatus.LOADED) {
            mStatus = MilkyFoxAdStatus.IDLE;
            for (IMilkyFoxInterstitialListener listener : mListeners) {
                listener.close(this);
            }
        }
    }

    private void tryToLoadNextAd() {
        if (mStatus == MilkyFoxAdStatus.LOADING && hasNextAd()) {
            mId++;
            try {
                BaseAdData data = mResponse.mList.get(mId);

                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        tryToLoadNextAd();
                    }
                };
                handler.postDelayed(runnable, TIMEOUT_INTERVAL);

                mCurController = InterstitialControllerFactory.getController(data, new IInterstitialControllerListener() {

                    @Override
                    public void load(BaseInterstitialController controller) {
                        if (controller == mCurController) {
                            handler.removeCallbacks(runnable);
                            notifyLoaded();
                        }
                    }

                    @Override
                    public void show(BaseInterstitialController controller) {
                        if (controller == mCurController) {
                            notifyShown();
                        }
                    }

                    @Override
                    public void click(BaseInterstitialController controller) {
                        if (controller == mCurController) {
                            notifyClicked();
                        }
                    }

                    @Override
                    public void close(BaseInterstitialController controller) {
                        if (controller == mCurController) {
                            notifyClosed();
                        }
                    }

                    @Override
                    public void fail(BaseInterstitialController controller, String error) {
                        if (controller == mCurController) {
                            handler.removeCallbacks(runnable);
                            MilkyFoxLog.log(String.format("preload failed: %s", error));
                            tryToLoadNextAd();
                        }
                    }
                });
                MilkyFoxLog.log(String.format("preload %s", mCurController.getDisplay()));
                mCurController.preload(mActivity);
            } catch (Throwable ex) {
                ex.printStackTrace();
                MilkyFoxLog.log(ex.getMessage(), true);
                tryToLoadNextAd();
            }
        } else {
            notifyFailed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCurController != null) {
            mCurController.onDestroy();
        }
    }

    private class InnerRequestListener extends BaseRequestListener<LoadInterstitialSuccessResponse> {
        @Override
        public void success(final LoadInterstitialSuccessResponse response) {
            if (isDestroyed()) {
                return;
            }
            mResponse = response;
            mId = -1;
            tryToLoadNextAd();
        }


        @Override
        public void error(final ErrorResponse response) {
            notifyFailed();
        }

        @Override
        public void canceled() {
            mStatus = MilkyFoxAdStatus.IDLE;
        }
    }
}
