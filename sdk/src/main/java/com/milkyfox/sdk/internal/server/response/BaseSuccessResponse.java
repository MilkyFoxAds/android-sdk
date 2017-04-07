/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.response;

public abstract class BaseSuccessResponse extends BaseResponse {
    public BaseSuccessResponse(String responseString, int requestId) {
        super(responseString, requestId);
    }

    @Override
    protected int getStatus() {
        return SUCCESS;
    }
}
