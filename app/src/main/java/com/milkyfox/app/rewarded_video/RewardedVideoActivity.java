/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.app.rewarded_video;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.milkyfox.app.R;
import com.milkyfox.app.utiles.ToastHelper;
import com.milkyfox.sdk.common.ads.video.IMilkyFoxRewardedVideoListener;
import com.milkyfox.sdk.common.ads.video.MilkyFoxRewardedVideo;

public class RewardedVideoActivity extends AppCompatActivity {
    public static final String AD_UNIT = "video_3403:4203";

    Toolbar mToolBar;
    EditText mEditText;
    TextView mInit;
    View mShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_video);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mInit = (TextView) findViewById(R.id.load);
        mInit.setText(getString(R.string.init));
        mShow = findViewById(R.id.show);

        mEditText.setText(AD_UNIT);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);


        mInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });

        mShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });

        updateUI();
    }

    private void updateUI() {
        boolean loaded = MilkyFoxRewardedVideo.isLoaded();
        mInit.setEnabled(!loaded);
        mShow.setEnabled(loaded);
    }

    private void show() {
        if (MilkyFoxRewardedVideo.isLoaded()) {
            MilkyFoxRewardedVideo.show();
        }
    }

    private void init() {
        mInit.setEnabled(false);
        String adUnit = String.valueOf(mEditText.getText());
        MilkyFoxRewardedVideo.initialize(this, adUnit);
        MilkyFoxRewardedVideo.setListener(new IMilkyFoxRewardedVideoListener() {
            @Override
            public void load() {
                updateUI();
                ToastHelper.showToast(RewardedVideoActivity.this, "load");
            }

            @Override
            public void show() {
                updateUI();
                ToastHelper.showToast(RewardedVideoActivity.this, "show");
            }

            @Override
            public void close() {
                updateUI();
                ToastHelper.showToast(RewardedVideoActivity.this, "close");
            }

            @Override
            public void start() {
                updateUI();
                ToastHelper.showToast(RewardedVideoActivity.this, "start");
            }

            @Override
            public void complete() {
                updateUI();
                ToastHelper.showToast(RewardedVideoActivity.this, "complete");
            }
        });
    }
}



