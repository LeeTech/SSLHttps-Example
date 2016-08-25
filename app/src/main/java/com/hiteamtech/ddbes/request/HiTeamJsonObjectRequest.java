package com.hiteamtech.ddbes.request;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.hiteamtech.ddbes.util.AESOperator;
import com.hiteamtech.ddbes.util.ConstanUtil;
import com.hiteamtech.ddbes.util.JsonUtil;
import com.hiteamtech.ddbes.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class HiTeamJsonObjectRequest extends Request<String> {

    /**
     * Default charset for JSON request.
     */
    protected static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private Response.Listener<String> mListener;

    private Response.ErrorListener mErrorListener;

    /**
     * request parameter
     */
    private Map<String, Object> mParams;


    public HiTeamJsonObjectRequest(int method, String url, Response.Listener<String> listener,
                                   Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        mErrorListener = errorListener;
    }


    @Override
    protected void onFinish() {
        super.onFinish();
    }

    @Override
    protected Map<String, Object> getParams() throws AuthFailureError {
        return getmParams();
    }

    public void setParams(Map<String, Object> mParams) {
        this.mParams = mParams;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String result = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(result,
                    HtHttpHeaderParser.parseCacheHeaders(response, ConstanUtil.CACHE_TIME));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }


    @Override
    public void deliverError(VolleyError error) {
        if (mErrorListener != null) {
            mErrorListener.onErrorResponse(error);
        }
    }

    @Override
    public byte[] getBody() {
        try {
            return getmParams() == null ? null : JsonUtil.toJson(getmParams()).getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            return null;
        }
    }

    private Map<String, Object> getmParams() {
        return mParams;
    }


    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }
}
