/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.milkyfox.app.interstitial.InterstitialActivity;
import com.milkyfox.app.interstitial.test.InterstitialActivityTest;
import com.milkyfox.app.recycler.MenuAdapter;
import com.milkyfox.app.recycler.MenuElement;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolBar;
    RecyclerView mRecyclerView;
    MenuAdapter mMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setHasFixedSize(true);

        List<MenuElement> menuElements = new LinkedList<>();
        menuElements.add(new MenuElement(getString(R.string.interstitial), InterstitialActivity.class));
        menuElements.add(new MenuElement(getString(R.string.interstitial_test), InterstitialActivityTest.class));

        mMenuAdapter = new MenuAdapter(this, menuElements);
        mRecyclerView.setAdapter(mMenuAdapter);

    }
}



