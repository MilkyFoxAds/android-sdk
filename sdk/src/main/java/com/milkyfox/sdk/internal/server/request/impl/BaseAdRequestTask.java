/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.request.impl;

import android.content.Context;

import com.milkyfox.sdk.internal.server.RequestManager;
import com.milkyfox.sdk.internal.server.listeners.IRequestListener;
import com.milkyfox.sdk.internal.server.request.RequestTask;
import com.milkyfox.sdk.internal.server.request.impl.data.LoadAdData;
import com.milkyfox.sdk.internal.server.response.BaseResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseAdRequestTask<S extends BaseResponse> extends RequestTask<S, LoadAdData> {

    public BaseAdRequestTask(RequestManager asyncManager, LoadAdData data, int requestId, IRequestListener requestListener) {
        super(asyncManager, data, requestId, requestListener);
    }

    @Override
    public void saveResult() {
    }


    @Override
    public Map<String, String> getParams() {
        Context context = getContext();
        Map<String, String> map = new HashMap<String, String>();
        map.put("ad_unit", mData.mAdUnit);
        map.put("os", "android");
        map.put("bundle", context.getPackageName());
        map.put("sign", "");
        return map;
    }
}
