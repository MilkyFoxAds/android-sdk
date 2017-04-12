/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.utils;

import java.util.TreeMap;

public class GetParamsHelper {

    public static String encodeGetParams(TreeMap<String, String> map){

        StringBuilder stringBuilder = new StringBuilder();
        if (map != null && map.size() > 0) {
            int i = 0;
            for (String key : map.keySet()) {
                if (i != 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(key);
                stringBuilder.append("=");
                stringBuilder.append(map.get(key));
                i++;
            }
        }

        return stringBuilder.toString();

    }

}
