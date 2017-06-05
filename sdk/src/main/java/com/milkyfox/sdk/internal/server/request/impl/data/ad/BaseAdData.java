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

public abstract class BaseAdData {
    public int mBannerId;

    public String getName() {
        return "base";
    }

    public JSONObject getContentsRepresentation() {
        return new JSONObject();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName());
        stringBuilder.append("(");
        JSONObject jsonObject = getContentsRepresentation();
        stringBuilder.append(jsonObject == null ? "null" : jsonObject.toString());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void parse(JSONObject jsonObject) throws JSONException {
        mBannerId = jsonObject.optInt("banner_id", -1);
    }

}
