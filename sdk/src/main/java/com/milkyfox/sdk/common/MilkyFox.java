/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.common;

public class MilkyFox {
    private static volatile boolean debugMode;

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static synchronized void setDebugMode(boolean debugMode) {
        MilkyFox.debugMode = debugMode;
    }
}
