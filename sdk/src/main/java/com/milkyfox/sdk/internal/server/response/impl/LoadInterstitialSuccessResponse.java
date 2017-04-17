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
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.AdMobInterstitialAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial.FacebookInterstitialAdData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadInterstitialSuccessResponse extends BaseLoadAdSuccessResponse {

    private enum AdType {admob, facebook}

    public LoadInterstitialSuccessResponse(String responseString, int requestId, LoadAdData loadAdData) {
        super(responseString, requestId, loadAdData);
    }

    @Override
    public void parse() throws JSONException {
        super.parse();
        JSONObject jsonObject = new JSONObject(mResponseString);
        if (jsonObject.has("data")) {
            JSONArray jsonArrayData = jsonObject.getJSONArray("data");
            int length = jsonArrayData.length();
            for (int i = 0; i < length; i++) {
                try {
                    JSONObject jsonObjectAd = (JSONObject) jsonArrayData.get(i);
                    AdType adType = AdType.valueOf(jsonObjectAd.getString("type"));
                    BaseAdData data = null;
                    switch (adType) {
                        case admob: {
                            data = new AdMobInterstitialAdData();
                            break;
                        }
                        case facebook: {
                            data = new FacebookInterstitialAdData();
                            break;
                        }
                    }
                    data.parse(jsonObjectAd);
                    mList.add(data);
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }

        logResult("Interstitial");
    }

}
