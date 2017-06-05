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
import com.milkyfox.sdk.internal.server.request.RequestMethods;
import com.milkyfox.sdk.internal.server.request.RequestTask;
import com.milkyfox.sdk.internal.server.request.impl.data.LogElement;
import com.milkyfox.sdk.internal.server.response.impl.LoadLogSuccessResponse;
import com.milkyfox.sdk.internal.utils.DeviceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

public class LogRequestTask extends RequestTask<LoadLogSuccessResponse, List<LogElement>> {

    public LogRequestTask(RequestManager asyncManager, int requestId, List<LogElement> logElements, IRequestListener requestListener) {
        super(asyncManager, logElements, requestId, requestListener);
    }


    @Override
    public void saveResult() {

    }

    @Override
    public String getUrlPath() {
        return ServerConstants.SERVER_URL + String.format(Locale.US, "/v%d/logrequest", ServerConstants.API_VERSION);
    }

    @Override
    public TreeMap<String, String> getParams() {
        return null;
    }

    @Override
    public LoadLogSuccessResponse obtainSuccessResponse(String responseString) throws JSONException, InvalidResponseExeption {
        return new LoadLogSuccessResponse(responseString, mRequestId);
    }

    @Override
    public RequestMethods getRequestMethod() {
        return RequestMethods.POST;
    }

    @Override
    public JSONObject getRequestJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", DeviceUtils.getCustomUUID(getContext()));
        JSONArray jsonArray = new JSONArray();
        for (LogElement logElement : mData) {
            jsonArray.put(logElement.toJsonObject());
        }
        jsonObject.put("log", jsonArray);
        return jsonObject;
    }
}
