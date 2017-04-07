/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.request;

import android.content.Context;

import com.milkyfox.sdk.internal.server.RequestManager;
import com.milkyfox.sdk.internal.server.listeners.IRequestListener;
import com.milkyfox.sdk.internal.server.response.BaseResponse;
import com.milkyfox.sdk.internal.server.response.impl.ErrorResponse;

public abstract class BaseRequestTask<D> implements IRequestTask {
    final protected RequestManager mRequestManager;
    final protected D mData;
    final protected int mRequestId;
    protected final Runnable mRequestRunnable;
    protected BaseResponse mResponse;
    protected ResponseStatus mResponseStatus = ResponseStatus.REQUEST_ERROR;
    IRequestListener mRequestListener;

    public BaseRequestTask(RequestManager asyncManager, D data, int requestId, IRequestListener requestListener) {
        mRequestManager = asyncManager;
        mData = data;
        mRequestId = requestId;
        mRequestListener = requestListener;

        mRequestRunnable = new RequestRunnable(this);
        mResponse = new ErrorResponse("", mRequestId, 0);
    }

    @Override
    public BaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public ResponseStatus getResponseStatus() {
        return mResponseStatus;
    }

    @Override
    public Runnable getThread() {
        return mRequestRunnable;
    }

    public void error(String response, int statusCode) {
        mResponse = new ErrorResponse(response, mRequestId, statusCode);
        mResponseStatus = ResponseStatus.REQUEST_ERROR;
        mRequestManager.handleState(this);
    }

    protected Context getContext() {
        return mRequestManager.getContext();
    }
}
