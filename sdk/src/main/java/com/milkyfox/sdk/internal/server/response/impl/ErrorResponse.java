/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.response.impl;

import com.milkyfox.sdk.internal.server.response.BaseResponse;

import org.json.JSONException;

public class ErrorResponse extends BaseResponse {
    public final int mStatusCode;

    public ErrorResponse(String responseString, int requestId, int statusCode) {
        super(responseString, requestId);
        this.mStatusCode = statusCode;
    }

    @Override
    public void parse() throws JSONException {

    }

    @Override
    protected int getStatus() {
        return ERROR;
    }
}
