/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.sdk.internal.server.request;

import android.os.Build;

import com.milkyfox.sdk.internal.server.RequestManager;
import com.milkyfox.sdk.internal.server.listeners.IRequestListener;
import com.milkyfox.sdk.internal.server.response.BaseResponse;
import com.milkyfox.sdk.internal.utils.MilkyFoxLog;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public abstract class RequestTask<S extends BaseResponse, D> extends BaseRequestTask<D> {
    private static final int TIMEOUT = 30000;

    private static final String GZIP = "gzip";

    public RequestTask(RequestManager asyncManager, D data, int requestId, IRequestListener requestListener) {
        super(asyncManager, data, requestId, requestListener);
    }

    public abstract String getUrlPath();

    public abstract Map<String, String> getParams();

    public RequestMethods getRequestMethod() {
        return RequestMethods.GET;
    }

    @Override
    public void doInBackground() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
        String response = "";
        URL url;
        StringBuilder urlStringBuilder = new StringBuilder(getUrlPath());

        Map<String, String> getParams = getParams();
        if (getParams != null && getParams.size() > 0) {
            urlStringBuilder.append("?");
            int i = 0;
            for (String key : getParams.keySet()) {
                if (i != 0) {
                    urlStringBuilder.append("&");
                    urlStringBuilder.append(key);
                    urlStringBuilder.append("=");
                    urlStringBuilder.append(getParams.get(key));
                }
                i++;
            }
        }

        String urlString = urlStringBuilder.toString();

        int statusCode = 0;

        String logString = mRequestId + ": ";
        HttpURLConnection connection = null;
        try {
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);

            connection.setRequestMethod(getRequestMethod().name());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoInput(true);

            InputStream inputStream;

            statusCode = connection.getResponseCode();

            boolean error = false;

            MilkyFoxLog.log(logString + "status = " + statusCode);
            if (statusCode >= 200 && statusCode <= 300) {
                if (GZIP.equals(connection.getHeaderField("Content-Encoding"))) {
                    inputStream = getGZIPInputStream(connection.getInputStream());
                } else {
                    inputStream = connection.getInputStream();
                }
            } else {
                if (GZIP.equals(connection.getHeaderField("Content-Encoding"))) {
                    inputStream = getGZIPInputStream(connection.getErrorStream());
                } else {
                    inputStream = connection.getErrorStream();
                }
                error = true;
            }

            response = streamToString(inputStream);

            if (error) {
                error(response, statusCode);
                return;
            } else {
                mResponse = obtainSuccessResponse(response);
                mResponse.parse();
                saveResult();
            }
            mResponseStatus = ResponseStatus.REQUEST_SUCCESS;
            mRequestManager.handleState(this);
        } catch (Exception e) {
            e.printStackTrace();
            error(response, statusCode);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            error(response, statusCode);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        MilkyFoxLog.log(logString + "response status = " + mResponseStatus);
    }

    private String streamToString(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        BufferedReader rd = new BufferedReader(new InputStreamReader(stream));
        String line;
        StringBuilder stringBuffer = new StringBuilder("");
        while ((line = rd.readLine()) != null) {
            stringBuffer.append(line);
        }
        return stringBuffer.toString();
    }

    private InputStream getGZIPInputStream(InputStream stream) {
        InputStream inputStream = null;
        try {
            inputStream = new GZIPInputStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    @Override
    public void onPostExecute() {
        if (mResponse.isSuccess()) {
            mRequestListener.success(mResponse);
        } else {
            mRequestListener.error(mResponse);
        }
    }

    public abstract S obtainSuccessResponse(String responseString) throws JSONException, InvalidResponseExeption;

    public abstract void saveResult();
}