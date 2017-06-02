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
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.AdMobRewardedVideoAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.AdcolonyRewardedVideoAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.AppLovinRewardedVideoAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.MoPubRewardedVideoAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.StartAppRewardedVideoAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.UnityAdsRewardedVideoAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video.VungleRewardedVideoAdData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadRewardedVideoSuccessResponse extends BaseLoadAdSuccessResponse {

    private enum AdType {admob, applovin, unityads, adcolony, vungle, mopub, startapp}

    public LoadRewardedVideoSuccessResponse(String responseString, int requestId, LoadAdData loadAdData) {
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
                            data = new AdMobRewardedVideoAdData();
                            break;
                        }
                        case applovin: {
                            data = new AppLovinRewardedVideoAdData();
                            break;
                        }
                        case unityads: {
                            data = new UnityAdsRewardedVideoAdData();
                            break;
                        }
                        case adcolony: {
                            data = new AdcolonyRewardedVideoAdData();
                            break;
                        }
                        case vungle: {
                            data = new VungleRewardedVideoAdData();
                            break;
                        }
                        case mopub: {
                            data = new MoPubRewardedVideoAdData();
                            break;
                        }
                        case startapp: {
                            data = new StartAppRewardedVideoAdData();
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

        logResult("RewardedVideo");
    }

}
