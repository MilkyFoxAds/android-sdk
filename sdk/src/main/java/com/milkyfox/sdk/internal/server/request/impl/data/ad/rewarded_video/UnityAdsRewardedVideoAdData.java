/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video;

import com.milkyfox.sdk.internal.server.request.impl.data.ad.SimpleAdData;

import org.json.JSONException;
import org.json.JSONObject;

public class UnityAdsRewardedVideoAdData extends SimpleAdData {

    public String mGameId;

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        super.parse(jsonObject);
        JSONObject jsonObjectSettings = jsonObject.getJSONObject("settings");
        mGameId = jsonObjectSettings.getString("game_id");
    }

    @Override
    public JSONObject getContentsRepresentation() {
        JSONObject jsonObject = super.getContentsRepresentation();
        try {
            jsonObject.put("game_id", mGameId);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String getName() {
        return "UnityAds";
    }
}
