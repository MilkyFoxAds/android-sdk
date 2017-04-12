/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.request.impl;

import com.milkyfox.sdk.internal.server.RequestManager;
import com.milkyfox.sdk.internal.server.ServerConstants;
import com.milkyfox.sdk.internal.server.listeners.IRequestListener;
import com.milkyfox.sdk.internal.server.request.InvalidResponseExeption;
import com.milkyfox.sdk.internal.server.request.impl.data.LoadAdData;
import com.milkyfox.sdk.internal.server.response.impl.LoadInterstitialSuccessResponse;

import org.json.JSONException;

import java.util.Locale;

public class InterstitialRequestTask extends BaseAdRequestTask<LoadInterstitialSuccessResponse> {

    public InterstitialRequestTask(RequestManager asyncManager, int requestId, LoadAdData loadAdData, IRequestListener requestListener) {
        super(asyncManager, loadAdData, requestId, requestListener);
    }


    @Override
    public LoadInterstitialSuccessResponse obtainSuccessResponse(String responseString) throws JSONException, InvalidResponseExeption {
        return new LoadInterstitialSuccessResponse(responseString, mRequestId, mData);
    }

    @Override
    public String getUrlPath() {
        return ServerConstants.SERVER_URL + String.format(Locale.US, "/v%d/interstitial", ServerConstants.API_VERSION);
    }
}
