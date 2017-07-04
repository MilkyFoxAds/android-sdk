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

import com.milkyfox.sdk.internal.utils.app_lifecycle.AppLifecycleHelper;
import com.milkyfox.sdk.internal.utils.app_lifecycle.IAppLifeCycleListener;

import java.util.Timer;
import java.util.TimerTask;

public class MilkyFoxInterstitialStaticAdapter {

    private static MilkyFoxInterstitial instance;
    private static final Integer TIMER_PERIOD = 3 * 60 * 1000;
    private static volatile Timer timer;
    private static Handler handler = new Handler();
    private static IMilkyFoxInterstitialListener listener;

    private static synchronized MilkyFoxInterstitial getInstance(Activity activity, String adUnit) {
        if (instance == null) {
            instance = new MilkyFoxInterstitial(activity, adUnit);
            instance.setListener(new IMilkyFoxInterstitialListener() {
                @Override
                public void load(MilkyFoxInterstitial interstitial) {
                    if (listener != null) {
                        listener.load(interstitial);
                    }
                }

                @Override
                public void fail(MilkyFoxInterstitial interstitial) {
                    if (listener != null) {
                        listener.fail(interstitial);
                    }
                }

                @Override
                public void show(MilkyFoxInterstitial interstitial) {
                    if (listener != null) {
                        listener.show(interstitial);
                    }
                }

                @Override
                public void click(MilkyFoxInterstitial interstitial) {
                    if (listener != null) {
                        listener.click(interstitial);
                    }
                }

                @Override
                public void close(MilkyFoxInterstitial interstitial) {
                    loadIfNeeded();
                    if (listener != null) {
                        listener.close(interstitial);
                    }
                }
            });
            instance.load();
        }
        return instance;
    }


    public synchronized static void initialize(Activity activity, String adUnit) {
        getInstance(activity, adUnit);
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

    public static void setListener(IMilkyFoxInterstitialListener listener) {
        MilkyFoxInterstitialStaticAdapter.listener = listener;
    }

    public static boolean isLoaded() {
        if (instance != null) {
            loadIfNeeded();
            return instance.isLoaded();
        }
        return false;
    }

    private static void loadIfNeeded() {
        if (instance != null) {
            boolean isLoaded = instance.isLoaded();
            if (!isLoaded) {
                instance.load();
            }
        }
    }

    public static void show() {
        if (instance != null) {
            instance.show();
        }
    }

    private static void runTimer() {
        synchronized (TIMER_PERIOD) {
            if (timer == null) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                loadIfNeeded();
                            }
                        });
                    }
                }, 0, TIMER_PERIOD);
            }
        }
    }

    private static void stopTimer() {
        synchronized (TIMER_PERIOD) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }


}
