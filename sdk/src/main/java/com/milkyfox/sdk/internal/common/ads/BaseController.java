/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.common.ads;

import android.app.Activity;
import android.content.Context;

import com.milkyfox.sdk.internal.server.request.impl.data.ad.BaseAdData;
import com.milkyfox.sdk.internal.utils.ManifestChecker;

public abstract class BaseController<T extends BaseAdData> {
    public T mData;

    public BaseController(T data) {
        this.mData = data;
    }

    public abstract void preload(Activity activity);

    public abstract void onDestroy();

    public abstract String getName();

    public final String getDisplay() {
        return getName() + " " + mData.getContentsRepresentation();
    }

    protected abstract void notifyFailed(String error);

    protected boolean isMetadataImplemented(Context context, String metaDataName) {
        boolean implemented = ManifestChecker.isMetadataImplemented(context, metaDataName);
        if (!implemented) {
            notifyFailed(metaDataName + " not implemented in AndroidManifest.xml");
        }
        return implemented;
    }

    protected boolean isActivityImplemented(Context context, String activityName) {
        boolean implemented = ManifestChecker.isActivityImplemented(context, activityName);
        if (!implemented) {
            notifyFailed(activityName + " not implemented in AndroidManifest.xml");
        }
        return implemented;
    }
}
