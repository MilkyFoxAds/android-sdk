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
import com.milkyfox.sdk.internal.utils.GetParamsHelper;
import com.milkyfox.sdk.internal.utils.HmacHelper;
import com.milkyfox.sdk.internal.utils.MilkyFoxLog;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public abstract class BaseAdRequestTask<S extends BaseResponse> extends RequestTask<S, LoadAdData> {

    public BaseAdRequestTask(RequestManager asyncManager, LoadAdData data, int requestId, IRequestListener requestListener) {
        super(asyncManager, data, requestId, requestListener);
    }

    @Override
    public void saveResult() {
    }


    @Override
    public TreeMap<String, String> getParams() {
        Context context = getContext();

        TreeMap<String, String> map = new TreeMap<String, String>();
        String adUnit = mData.mAdUnit;

        String[] split = adUnit.split(":");
        if (split.length == 2) {
            String key = split[1];

            map.put("bundle", context.getPackageName());
            map.put("ad_unit", adUnit);
            map.put("os", "android");
            String sign = null;
            String data = GetParamsHelper.encodeGetParams(map);
            try {
//                sign = HmacHelper.encode(key, "bundle=com.milkyfox.app&ad_unit=interstitial_3402&os=android");
                sign = HmacHelper.encode(key, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("sign", sign);
            map.put("ad_unit", split[0]);

//            map.put("sign", "88631b284b978c9bb82733b17607444d4da16e7bd1c13e80282eabcfc12ab5f7");
        } else {
            MilkyFoxLog.log("invalid ad_unit", true);
        }
        return map;
    }
}
