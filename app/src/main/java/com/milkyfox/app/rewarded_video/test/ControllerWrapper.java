/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.app.rewarded_video.test;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.milkyfox.app.R;
import com.milkyfox.app.utiles.ToastHelper;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.BaseInterstitialController;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.IInterstitialControllerListener;
import com.milkyfox.sdk.internal.common.ads.interstitial.controller.InterstitialControllerFactory;
import com.milkyfox.sdk.internal.common.ads.video.controller.BaseRewardedVideoController;
import com.milkyfox.sdk.internal.common.ads.video.controller.IRewardedVideoControllerListener;
import com.milkyfox.sdk.internal.common.ads.video.controller.RewardedVideoControllerFactory;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.BaseAdData;

public class ControllerWrapper {

    View mView;
    BaseAdData mBaseAdData;
    Activity mActivity;
    TextView mName;
    View mLoad;
    View mShow;

    BaseRewardedVideoController mController;

    public ControllerWrapper(View view, BaseAdData baseAdData, Activity activity) {
        this.mView = view;
        this.mBaseAdData = baseAdData;
        this.mActivity = activity;
        mName = (TextView) view.findViewById(R.id.name);
        mLoad = view.findViewById(R.id.load);
        mShow = view.findViewById(R.id.show);

        mShow.setEnabled(false);
        try {

            mController = RewardedVideoControllerFactory.getController(mBaseAdData, new IRewardedVideoControllerListener() {
                @Override
                public void load(BaseRewardedVideoController rewardedVideoController) {
                    mShow.setEnabled(true);
                    ToastHelper.showToast(mActivity, "load");
                }

                @Override
                public void fail(BaseRewardedVideoController rewardedVideoController, String error) {
                    mLoad.setEnabled(true);
                    ToastHelper.showToast(mActivity, "fail");
                }

                @Override
                public void show(BaseRewardedVideoController rewardedVideoController) {
                    ToastHelper.showToast(mActivity, "show");
                }

                @Override
                public void close(BaseRewardedVideoController rewardedVideoController) {
                    ToastHelper.showToast(mActivity, "close");
                }

                @Override
                public void start(BaseRewardedVideoController rewardedVideoController) {
                    ToastHelper.showToast(mActivity, "start");
                }

                @Override
                public void complete(BaseRewardedVideoController rewardedVideoController) {
                    ToastHelper.showToast(mActivity, "complete");
                }
            });

            mLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    load();
                }
            });

            mShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    show();
                }
            });

            mName.setText(mController.getDisplay());
        } catch (Throwable ex) {
            mShow.setEnabled(false);
            mLoad.setEnabled(false);
            mName.setText(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void load(){
        mLoad.setEnabled(false);
        mController.preload(mActivity);
    }

    private void show(){
        mShow.setEnabled(false);
        mLoad.setEnabled(true);
        mController.show(mActivity);
    }
}
