/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.response.impl;

import com.milkyfox.sdk.internal.server.request.impl.data.LoadAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.BaseAdData;
import com.milkyfox.sdk.internal.server.response.BaseSuccessResponse;
import com.milkyfox.sdk.internal.utils.MilkyFoxLog;

import org.json.JSONException;

import java.util.LinkedList;
import java.util.List;

public class BaseLoadAdSuccessResponse extends BaseSuccessResponse {

    public List<BaseAdData> mList = new LinkedList<BaseAdData>();
    protected LoadAdData mLoadAdData;

    public BaseLoadAdSuccessResponse(String responseString, int requestId, LoadAdData loadAdData) {
        super(responseString, requestId);
        mLoadAdData = loadAdData;
    }

    @Override
    public void parse() throws JSONException {
    }

    protected void logResult(String name) {
        try {
            if (MilkyFoxLog.isLogEnabled()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("loaded ");
                stringBuilder.append(name);
                stringBuilder.append(": ");
                int i = 0;
                for (BaseAdData baseAdData : mList) {
                    if (i != 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(baseAdData.toString());
                    i++;
                }
                MilkyFoxLog.log(stringBuilder.toString());
            }
        } catch (Throwable ex) {
        }
    }
}
