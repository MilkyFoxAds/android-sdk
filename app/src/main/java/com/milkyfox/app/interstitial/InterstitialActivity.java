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

import com.milkyfox.app.R;
import com.milkyfox.sdk.common.ads.interstitial.IMilkyFoxInterstitialListener;
import com.milkyfox.sdk.common.ads.interstitial.MilkyFoxInterstitial;

public class InterstitialActivity extends AppCompatActivity {
    public static final String AD_UNIT = "interstitial_test";

    Toolbar mToolBar;

    MilkyFoxInterstitial mMilkyFoxInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        mMilkyFoxInterstitial = new MilkyFoxInterstitial(this, AD_UNIT);
        mMilkyFoxInterstitial.addListener(new IMilkyFoxInterstitialListener() {
            @Override
            public void load(MilkyFoxInterstitial interstitial) {
                if(mMilkyFoxInterstitial.isLoaded()) {
                    mMilkyFoxInterstitial.show();
                }
            }

            @Override
            public void fail(MilkyFoxInterstitial interstitial) {

            }

            @Override
            public void show(MilkyFoxInterstitial interstitial) {

            }

            @Override
            public void click(MilkyFoxInterstitial interstitial) {

            }

            @Override
            public void close(MilkyFoxInterstitial interstitial) {

            }
        });

        mMilkyFoxInterstitial.load();
    }
}



