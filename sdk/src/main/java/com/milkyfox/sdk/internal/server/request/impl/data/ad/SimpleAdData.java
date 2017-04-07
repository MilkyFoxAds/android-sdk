/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.request.impl.data.ad;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class SimpleAdData extends BaseAdData {
    public String mAdUnit;

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        super.parse(jsonObject);
        JSONObject jsonObjectSettings = jsonObject.getJSONObject("settings");
        mAdUnit = jsonObjectSettings.getString("ad_unit");
    }

    @Override
    public JSONObject getContentsRepresentation() {
        JSONObject jsonObject = super.getContentsRepresentation();
        try {
            jsonObject.put("ad_unit", mAdUnit);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
