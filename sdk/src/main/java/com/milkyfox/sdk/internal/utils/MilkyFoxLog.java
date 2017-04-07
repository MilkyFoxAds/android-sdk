/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.utils;

import android.os.Environment;
import android.util.Log;

import com.milkyfox.sdk.common.MilkyFox;
import com.milkyfox.sdk.internal.server.ServerConstants;

import java.io.File;

public class MilkyFoxLog {
    private static String TAG = "MilkyFoxLog_";

    private static boolean storageChecked = false;
    private static boolean forceLogDir = false;

    public static void log(String message) {
        MilkyFoxLog.log(message, false);
    }

    public static void log(String message, boolean force) {
        if (force || isLogEnabled()) {
            Log.d(TAG, String.valueOf(message));
        }
    }

    public static boolean isLogEnabled() {
        checkStorage();
        return forceLogDir || MilkyFox.isDebugMode();
    }

    private static void checkStorage() {
        if (!storageChecked) {
            storageChecked = true;
            try {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    File externalStorageDir = Environment.getExternalStorageDirectory();
                    File checkDir = new File(externalStorageDir, "MilkyFoxLog");
                    if (checkDir.exists()) {
                        forceLogDir = true;
                        Log.d(TAG, "MilkyFoxSdk version " + ServerConstants.SDK_VERSION);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
