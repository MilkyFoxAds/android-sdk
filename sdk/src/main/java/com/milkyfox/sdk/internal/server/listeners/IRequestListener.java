/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.listeners;


import com.milkyfox.sdk.internal.server.response.BaseResponse;

public interface IRequestListener<S extends BaseResponse, E extends BaseResponse> {
    public void success(S response);

    public void error(E response);

    public void canceled();
}
