/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.request;

public class RequestRunnable implements Runnable {
    IRequestTask mTask;

    public RequestRunnable(IRequestTask task) {
        mTask = task;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        mTask.doInBackground();
    }
}
