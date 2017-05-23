/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.app.interstitial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.milkyfox.app.R;
import com.milkyfox.app.utiles.ToastHelper;
import com.milkyfox.sdk.common.ads.interstitial.IMilkyFoxInterstitialListener;
import com.milkyfox.sdk.common.ads.interstitial.MilkyFoxInterstitial;

public class InterstitialActivity extends AppCompatActivity {
    public static final String AD_UNIT = "interstitial_3402:4203";

    Toolbar mToolBar;
    EditText mEditText;
    View mLoad;
    View mShow;

    String currentAdUnit;

    MilkyFoxInterstitial mMilkyFoxInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mLoad = findViewById(R.id.load);
        mShow = findViewById(R.id.show);

        mEditText.setText(AD_UNIT);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);


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

        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyAd();
    }

    private void destroyAd() {
        if (mMilkyFoxInterstitial != null) {
            mMilkyFoxInterstitial.onDestroy();
            mMilkyFoxInterstitial = null;
        }
    }

    private void updateUI() {
        mShow.setEnabled(mMilkyFoxInterstitial != null && mMilkyFoxInterstitial.isLoaded());
    }

    private void show() {
        if (mMilkyFoxInterstitial != null && mMilkyFoxInterstitial.isLoaded()) {
            mMilkyFoxInterstitial.show();
        }
    }

    private void load() {
        String newAdUnit = String.valueOf(mEditText.getText());
        if (!newAdUnit.equals(currentAdUnit)) {
            destroyAd();

            mMilkyFoxInterstitial = new MilkyFoxInterstitial(this, AD_UNIT);
            mMilkyFoxInterstitial.setListener(new IMilkyFoxInterstitialListener() {
                @Override
                public void load(MilkyFoxInterstitial interstitial) {
                    ToastHelper.showToast(InterstitialActivity.this, "load");
                    updateUI();
                }

                @Override
                public void fail(MilkyFoxInterstitial interstitial) {
                    ToastHelper.showToast(InterstitialActivity.this, "fail");
                    updateUI();
                }

                @Override
                public void show(MilkyFoxInterstitial interstitial) {
                    ToastHelper.showToast(InterstitialActivity.this, "show");
                }

                @Override
                public void click(MilkyFoxInterstitial interstitial) {
                    ToastHelper.showToast(InterstitialActivity.this, "click");
                }

                @Override
                public void close(MilkyFoxInterstitial interstitial) {
                    ToastHelper.showToast(InterstitialActivity.this, "close");
                    updateUI();
                }
            });

            currentAdUnit = newAdUnit;
        }
        mMilkyFoxInterstitial.load();
    }
}



