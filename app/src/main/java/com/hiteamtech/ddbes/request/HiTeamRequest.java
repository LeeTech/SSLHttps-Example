package com.hiteamtech.ddbes.request;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hiteamtech.ddbes.R;
import com.hiteamtech.ddbes.util.ConstanUtil;
import com.hiteamtech.ddbes.util.StringUtil;

import java.util.Map;

/**
 * ClassName: HiTeamRequest
 * Description:  网络请求类
 * author junli Lee mingyangnc@163.com
 * date 2015/11/9 18:30
 */
public class HiTeamRequest {

    private HiTeamJsonObjectRequest mRequest;

    private Map<String, Object> params;

    private static final Gson mGson;

    static {
        mGson = new Gson();
    }

    public HiTeamJsonObjectRequest send(Context context, int method, String action,
                                        RequestQueue queue, HtResponseListener<String> listener, boolean isShouldCache) {

        if (listener != null) {
            listener.onStart();
        }

        mRequest = new HiTeamJsonObjectRequest(method,
                ConstanUtil.BASE_URL + action,
                new OnResponseStringListener(context, listener),
                new OnResponseStringErrorListener(queue, listener, context));

        initRequestConfig(mRequest, action, queue, isShouldCache);

        return mRequest;
    }

    public <T> HiTeamJsonObjectRequest send(Context context, int method, String action,
                                            RequestQueue queue, HtResponseListener<T> listener,
                                            Class<T> clazz, boolean isShouldCache) {

        if (listener != null) {
            listener.onStart();
        }

        mRequest = new HiTeamJsonObjectRequest(method, ConstanUtil.BASE_URL + action,
                new OnResponseListener(context, listener, clazz),
                new OnResponseErrorListener(queue, listener, context, clazz));

        initRequestConfig(mRequest, action, queue, isShouldCache);

        return mRequest;
    }

    private void initRequestConfig(HiTeamJsonObjectRequest request, String action, RequestQueue queue, boolean isShouldCache) {
        request.setShouldCache(isShouldCache); // 是否加入缓存
        request.setParams(getParams()); // 请求参数
        request.setTag(action); // 设置请求tag,用于取消请求
        //设置请求超时时间与请求重试次数
        request.setRetryPolicy(new DefaultRetryPolicy(9 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request); // 加入请求队列
    }

    private class OnResponseErrorListener<T> implements Response.ErrorListener {

        private Context mContext;

        private RequestQueue mQueue;

        private HtResponseListener<T> listener;

        private Class<T> clazz;

        public OnResponseErrorListener(RequestQueue queue, HtResponseListener<T> listener, Context context, Class<T> clazz) {

            this.mContext = context;
            this.mQueue = queue;
            this.listener = listener;
            this.clazz = clazz;
        }

        @Override
        public void onErrorResponse(VolleyError error) {

            String cacheStr = null;

            if (mQueue.getCache().get(mRequest.getCacheKey()) != null) {
                cacheStr = new String(mQueue.getCache().get(mRequest.getCacheKey()).data).toString();
            }

            try {
                if (listener != null) {
                    listener.onFail(mContext.getResources().getString(R.string.errorMsg), getResponseData(cacheStr, clazz));
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onFail(mContext.getResources().getString(R.string.errorMsg), getResponseData(cacheStr, clazz));
                }
            }

        }
    }

    private <T> T getResponseData(String json, Class<T> clazz) {
        if (StringUtil.isNotBlankAndEmpty(json)) {
            return mGson.fromJson(json, clazz);
        }
        return null;
    }


    private class OnResponseStringErrorListener implements Response.ErrorListener {

        private Context mContext;

        private RequestQueue mQueue;

        private HtResponseListener<String> listener;

        public OnResponseStringErrorListener(RequestQueue queue, HtResponseListener<String> listener, Context context) {
            this.mContext = context;
            this.mQueue = queue;
            this.listener = listener;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            String cacheStr = null;
            if (mQueue.getCache().get(mRequest.getCacheKey()) != null) {
                cacheStr = new String(mQueue.getCache().get(mRequest.getCacheKey()).data).toString();
            }

            try {
                if (this.listener != null) {
                    this.listener.onFail(mContext.getResources().getString(R.string.errorMsg), cacheStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (this.listener != null) {
                    this.listener.onFail(mContext.getResources().getString(R.string.errorMsg), cacheStr);
                }
            }

        }
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

}
