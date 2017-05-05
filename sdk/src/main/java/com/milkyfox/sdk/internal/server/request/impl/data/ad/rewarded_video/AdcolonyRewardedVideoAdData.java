/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.request.impl.data.ad.rewarded_video;

import com.milkyfox.sdk.internal.server.request.impl.data.ad.BaseAdData;
import com.milkyfox.sdk.internal.server.request.impl.data.ad.SimpleAdData;

import org.json.JSONException;
import org.json.JSONObject;

public class AdcolonyRewardedVideoAdData extends BaseAdData {

    public String mAppId;
    public String mZoneId;

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        super.parse(jsonObject);
        JSONObject jsonObjectSettings = jsonObject.getJSONObject("settings");
        mAppId = jsonObjectSettings.getString("app_id");
        mZoneId = jsonObjectSettings.getString("zone_id");
    }

    @Override
    public JSONObject getContentsRepresentation() {
        JSONObject jsonObject = super.getContentsRepresentation();
        try {
            jsonObject.put("app_id", mAppId);
            jsonObject.put("zone_id", mZoneId);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String getName() {
        return "Adcolony";
    }
}
