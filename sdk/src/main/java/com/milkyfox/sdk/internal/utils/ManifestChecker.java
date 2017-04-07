/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

public class ManifestChecker {

    public static boolean isMetadataImplemented(Context context, String metaDataName) {
        boolean implemented = false;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String metaData = bundle.getString(metaDataName);
            implemented = !TextUtils.isEmpty(metaData);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return implemented;
    }

    public static boolean isActivityImplemented(Context context, String activityName) {
        try {
            Intent intent = new Intent();
            intent.setClassName(context, activityName);
            if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                return true;
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean isServiceImplemented(Context context, String serviceName) {
        try {
            Intent intent = new Intent();
            intent.setClassName(context, serviceName);
            if (context.getPackageManager().resolveService(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                return true;
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
