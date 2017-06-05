/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.response.impl;

import com.milkyfox.sdk.internal.server.response.BaseSuccessResponse;

import org.json.JSONException;

public class LoadLogSuccessResponse extends BaseSuccessResponse {

    public LoadLogSuccessResponse(String responseString, int requestId) {
        super(responseString, requestId);
    }

    @Override
    public void parse() throws JSONException {
    }

}
