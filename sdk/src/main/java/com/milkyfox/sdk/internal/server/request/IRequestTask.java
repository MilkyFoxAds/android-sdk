/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.request;

import com.milkyfox.sdk.internal.server.response.BaseResponse;

public interface IRequestTask {
    void doInBackground();

    void onPostExecute();

    Runnable getThread();

    BaseResponse getResponse();

    ResponseStatus getResponseStatus();
}
