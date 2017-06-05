/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.UUID;

public class DeviceUtils {
    public static final String UID = "uid";
    public static final String CUSTOM_UUID = "custom_uuid";
    private static final String PREF_SETTINGS_FILE_NAME = "com.milkyfox.sdk.uid.xml";

    public static String getCustomUUID(Context context) {
        String uuid = "";
        try {
            uuid = getCustomUUIDFromPrefs(context);
            if (TextUtils.isEmpty(uuid)) {
                uuid = UUID.randomUUID().toString();
                saveCustomUUIDToPrefs(uuid, context);
            }
        }catch (Throwable ex){
            ex.printStackTrace();
        }
        return uuid;
    }

    private static String getCustomUUIDFromPrefs(Context context) {
        String uuid = null;
        SharedPreferences settings = context.getSharedPreferences(PREF_SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
        if (settings != null) {
            uuid = settings.getString(CUSTOM_UUID, null);
        }
        return uuid;
    }

    public static void saveCustomUUIDToPrefs(String uuid, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREF_SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(CUSTOM_UUID, uuid);
        editor.apply();
    }
}
