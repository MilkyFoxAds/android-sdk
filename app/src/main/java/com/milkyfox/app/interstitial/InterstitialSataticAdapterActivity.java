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
import android.widget.TextView;

import com.milkyfox.app.R;
import com.milkyfox.app.utiles.ToastHelper;
import com.milkyfox.sdk.common.ads.interstitial.IMilkyFoxInterstitialListener;
import com.milkyfox.sdk.common.ads.interstitial.MilkyFoxInterstitial;
import com.milkyfox.sdk.common.ads.interstitial.MilkyFoxInterstitialStaticAdapter;

public class InterstitialSataticAdapterActivity extends AppCompatActivity {
    public static final String AD_UNIT = "interstitial_3405:4204";
//    public static final String AD_UNIT = "video_3403:4203";

    Toolbar mToolBar;
    EditText mEditText;
    TextView mInit;
    View mShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_static_adapter);
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
        boolean loaded = MilkyFoxInterstitialStaticAdapter.isLoaded();
        mShow.setEnabled(loaded);
    }

    private void show() {
        if (MilkyFoxInterstitialStaticAdapter.isLoaded()) {
            MilkyFoxInterstitialStaticAdapter.show();
        }
    }

    private void init() {
        mInit.setEnabled(false);
        String adUnit = String.valueOf(mEditText.getText());
        MilkyFoxInterstitialStaticAdapter.initialize(this, adUnit);
        MilkyFoxInterstitialStaticAdapter.setListener(
                new IMilkyFoxInterstitialListener() {
                    @Override
                    public void load(MilkyFoxInterstitial interstitial) {
                        updateUI();
                        ToastHelper.showToast(InterstitialSataticAdapterActivity.this, "load");
                    }

                    @Override
                    public void fail(MilkyFoxInterstitial interstitial) {
                        updateUI();
                        ToastHelper.showToast(InterstitialSataticAdapterActivity.this, "fail");
                    }

                    @Override
                    public void show(MilkyFoxInterstitial interstitial) {
                        updateUI();
                        ToastHelper.showToast(InterstitialSataticAdapterActivity.this, "show");
                    }

                    @Override
                    public void click(MilkyFoxInterstitial interstitial) {
                        updateUI();
                        ToastHelper.showToast(InterstitialSataticAdapterActivity.this, "click");
                    }

                    @Override
                    public void close(MilkyFoxInterstitial interstitial) {
                        updateUI();
                        ToastHelper.showToast(InterstitialSataticAdapterActivity.this, "close");
                    }
                }
        );
    }
}



