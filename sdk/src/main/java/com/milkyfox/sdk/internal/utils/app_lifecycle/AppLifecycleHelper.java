/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.utils.app_lifecycle;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import java.util.List;

public class AppLifecycleHelper {
    public static void addListener(Context context, final IAppLifeCycleListener listener) {
        if (context != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    activity.getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                        @Override
                        public void onActivityCreated(Activity activity, Bundle bundle) {

                        }

                        @Override
                        public void onActivityStarted(Activity activity) {
                            update(activity);
                        }

                        @Override
                        public void onActivityResumed(Activity activity) {

                        }

                        @Override
                        public void onActivityPaused(Activity activity) {

                        }

                        @Override
                        public void onActivityStopped(Activity activity) {
                            update(activity);
                        }

                        @Override
                        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

                        }

                        @Override
                        public void onActivityDestroyed(Activity activity) {
                        }

                        private void update(Activity act) {
                            if (isRunning(act)) {
                                listener.start();
                            } else {
                                listener.stop();
                            }
                        }

                        boolean isRunning(Activity act) {
                            int countForeground = 0;
                            try {
                                ActivityManager activityManager = (ActivityManager) act.getSystemService(Context.ACTIVITY_SERVICE);
                                final List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
                                if (processInfos != null) {
                                    for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
                                        if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                                            countForeground++;
                                        }
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            return countForeground > 0;
                        }
                    });
                }
            }
        }
    }
}
