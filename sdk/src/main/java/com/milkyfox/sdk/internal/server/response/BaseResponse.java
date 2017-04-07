/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.response;

import org.json.JSONException;

public abstract class BaseResponse {
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    public final int mRequestId;
    protected String mResponseString;

    public BaseResponse(String responseString, int requestId) {
        this.mResponseString = responseString;
        this.mRequestId = requestId;
    }

    @Override
    public String toString() {
        return mResponseString;
    }

    public String getResponseString() {
        return mResponseString;
    }

    protected int getStatus() {
        return SUCCESS;
    }

    public boolean isSuccess() {
        return getStatus() == SUCCESS;
    }

    public abstract void parse() throws JSONException;
}
