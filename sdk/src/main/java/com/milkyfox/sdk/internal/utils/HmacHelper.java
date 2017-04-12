/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.utils;

import android.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacHelper {

    public static String encode(String key, String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec sk = new SecretKeySpec(key.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(sk);
        byte[] result = mac.doFinal(data.getBytes("UTF-8"));
        return Base64.encodeToString(result , Base64.DEFAULT);
    }
}
