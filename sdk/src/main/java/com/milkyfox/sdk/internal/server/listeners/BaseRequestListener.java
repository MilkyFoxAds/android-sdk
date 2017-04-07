/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.listeners;

import com.milkyfox.sdk.internal.server.response.BaseResponse;
import com.milkyfox.sdk.internal.server.response.impl.ErrorResponse;

public abstract class BaseRequestListener<S extends BaseResponse> implements IRequestListener<S, ErrorResponse> {
}
