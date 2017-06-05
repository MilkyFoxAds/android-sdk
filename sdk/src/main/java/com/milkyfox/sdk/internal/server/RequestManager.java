/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.milkyfox.sdk.internal.server.listeners.IRequestListener;
import com.milkyfox.sdk.internal.server.request.IRequestTask;
import com.milkyfox.sdk.internal.server.request.impl.InterstitialRequestTask;
import com.milkyfox.sdk.internal.server.request.impl.LogRequestTask;
import com.milkyfox.sdk.internal.server.request.impl.RewardedVideoRequestTask;
import com.milkyfox.sdk.internal.server.request.impl.data.LoadAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.LogElement;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RequestManager {

    // Sets the amount of time an idle thread will wait for a task before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT;
    @SuppressLint("StaticFieldLeak")
    private static volatile RequestManager instance;
    private static int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();

    static {
        // The time unit for "keep alive" is in seconds
        KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    }

    //requests
    private final BlockingQueue<Runnable> mRequestBlockingQueue;
    private final ThreadPoolExecutor mRequestThreadPool;
    //log
    private final BlockingQueue<Runnable> mLogBlockingQueue;
    private final ThreadPoolExecutor mLogThreadPool;

    private volatile Integer mId = 0;
    private final Boolean syncId = false;
    private Handler mHandler;
    private Context mContext;

    private RequestManager(Context context) {
        mContext = context;

        mRequestBlockingQueue = new LinkedBlockingQueue<Runnable>();
        mRequestThreadPool = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mRequestBlockingQueue);

        mLogBlockingQueue = new LinkedBlockingQueue<Runnable>();
        mLogThreadPool = new ThreadPoolExecutor(
                1,       // Initial pool size
                1,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mRequestBlockingQueue);

        mHandler = new InnerHandler(this);
    }

    public static RequestManager getInstance(Context context) {
        RequestManager localInstance = instance;
        if (localInstance == null) {
            synchronized (RequestManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RequestManager(context.getApplicationContext());
                }
            }
        }
        return localInstance;
    }

    private void handleAnyMessage(Message msg) {
        IRequestTask requestTask = (IRequestTask) msg.obj;
        requestTask.onPostExecute();
    }

    public IRequestTask loadInterstitial(LoadAdData loadAdData, IRequestListener requestListener) {
        int requestId = getRequestId();
        IRequestTask requestTask = new InterstitialRequestTask(this, requestId, loadAdData, requestListener);
        mRequestThreadPool.execute(requestTask.getThread());
        return requestTask;
    }

    public IRequestTask loadRewardedVideo(LoadAdData loadAdData, IRequestListener requestListener) {
        int requestId = getRequestId();
        IRequestTask requestTask = new RewardedVideoRequestTask(this, requestId, loadAdData, requestListener);
        mRequestThreadPool.execute(requestTask.getThread());
        return requestTask;
    }

    public IRequestTask log(List<LogElement> logElements, IRequestListener requestListener) {
        int requestId = getRequestId();
        IRequestTask requestTask = new LogRequestTask(this, requestId, logElements, requestListener);
        mLogThreadPool.execute(requestTask.getThread());
        return requestTask;
    }

    private int getRequestId() {
        int requestId;
        synchronized (syncId) {
            requestId = mId;
            mId++;
        }
        return requestId;
    }

    public void handleState(IRequestTask requestTask) {
        mHandler.obtainMessage(requestTask.getResponseStatus().ordinal(), requestTask).sendToTarget();
    }

    public void cancelAllTasks() {
        mRequestBlockingQueue.clear();
        mLogBlockingQueue.clear();
    }

    public Context getContext() {
        return mContext;
    }

    private static class InnerHandler extends Handler {
        private final WeakReference<RequestManager> weakTarget;

        InnerHandler(RequestManager target) {
            weakTarget = new WeakReference<RequestManager>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            RequestManager target = weakTarget.get();
            if (target != null) {
                target.handleAnyMessage(msg);
            }
        }
    }
}
