package com.hiteamtech.ddbes.request;

import android.content.Context;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.hiteamtech.ddbes.R;
import com.hiteamtech.ddbes.util.StringUtil;

/**
 * ClassName: OnResponseListener
 * Description TODO 请描述这个文件
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/4/19 15:16
 */
public class OnResponseListener<T> implements Response.Listener<String> {

    private HtResponseListener<T> listener;

    private Class<T> clazz;

    private Context context;

    private static final Gson mGson;

    static {
        mGson = new Gson();
    }

    public OnResponseListener(Context context, HtResponseListener<T> listener, Class<T> clazz) {
        this.context = context;
        this.listener = listener;
        this.clazz = clazz;
    }

    @Override
    public void onResponse(String response) {
        try {
            listener.onSuccess(getResponseData(response, clazz));
        } catch (Exception e) {
            e.printStackTrace();
            if (this.listener != null) {
                this.listener.onFail(context.getResources().getString(R.string.errorMsg), null);
            }
        }
    }

    private T getResponseData(String json, Class<T> clazz) {
        if (StringUtil.isNotBlankAndEmpty(json)) {
            return mGson.fromJson(json, clazz);
        }
        return null;
    }
}
