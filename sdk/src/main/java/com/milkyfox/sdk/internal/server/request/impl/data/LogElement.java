/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.request.impl.data;

import org.json.JSONException;
import org.json.JSONObject;

public class LogElement {
    private int bannerId;
    private long duration;
    private boolean loaded;

    public LogElement(int bannerId, long duration, boolean loaded) {
        this.bannerId = bannerId;
        this.duration = duration;
        this.loaded = loaded;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("banner_id", bannerId);
            jsonObject.put("duration", duration);
            if(loaded){
                jsonObject.put("loaded", loaded);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
