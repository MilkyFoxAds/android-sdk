/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.request.impl.data.ad.interstitial;

import com.milkyfox.sdk.internal.server.request.impl.data.ad.SimpleAdData;

import org.json.JSONException;
import org.json.JSONObject;

public class AppLovinInterstitialAdData extends SimpleAdData {

    public String mPlacement;

    public AppLovinInterstitialAdData() {
    }

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        super.parse(jsonObject);
        JSONObject jsonObjectSettings = jsonObject.getJSONObject("settings");
        mPlacement = jsonObjectSettings.optString("placement", "");
    }

    @Override
    public JSONObject getContentsRepresentation() {
        JSONObject jsonObject = super.getContentsRepresentation();
        try {
            jsonObject.put("placement", mPlacement);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String getName() {
        return "Applovin";
    }
}
