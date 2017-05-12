/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.app.rewarded_video.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.milkyfox.app.R;
import com.milkyfox.app.utiles.ToastHelper;
import com.milkyfox.sdk.internal.server.RequestManager;
import com.milkyfox.sdk.internal.server.listeners.BaseRequestListener;
import com.milkyfox.sdk.internal.server.request.impl.data.LoadAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.BaseAdData;
import com.milkyfox.sdk.internal.server.response.impl.ErrorResponse;
import com.milkyfox.sdk.internal.server.response.impl.LoadRewardedVideoSuccessResponse;

public class RewardedVideoActivityTest extends AppCompatActivity {
    public static final String AD_UNIT = "video_3403:4203";

    Toolbar mToolBar;
    EditText mEditText;
    View mLoad;
    View mShow;

    LinearLayout mControllersContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_test);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mLoad = findViewById(R.id.load);
        mShow = findViewById(R.id.show);
        mControllersContainer = (LinearLayout) findViewById(R.id.controllers_container);

        mEditText.setText(AD_UNIT);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);


        mLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });

        mShow.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void load() {
        mControllersContainer.removeAllViews();
        String newAdUnit = String.valueOf(mEditText.getText());
        RequestManager.getInstance(this).loadRewardedVideo(
                new LoadAdData(newAdUnit),
                new InnerRequestListener());

    }

    private class InnerRequestListener extends BaseRequestListener<LoadRewardedVideoSuccessResponse> {

        @Override
        public void success(LoadRewardedVideoSuccessResponse response) {
            LayoutInflater layoutInflater = LayoutInflater.from(RewardedVideoActivityTest.this);
            for (BaseAdData baseAdData : response.mList) {
                View view = layoutInflater.inflate(R.layout.controller, null);
                ControllerWrapper controllerWrapper = new ControllerWrapper(view, baseAdData, RewardedVideoActivityTest.this);
                mControllersContainer.addView(view);
            }
        }

        @Override
        public void error(ErrorResponse response) {
            ToastHelper.showToast(RewardedVideoActivityTest.this, "fail " + response.mStatusCode);
        }

        @Override
        public void canceled() {

        }
    }
}



