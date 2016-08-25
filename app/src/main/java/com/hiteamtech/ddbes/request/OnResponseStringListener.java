package com.hiteamtech.ddbes.request;

import android.content.Context;

import com.android.volley.Response;
import com.hiteamtech.ddbes.R;

/**
 * ClassName: OnResponseStringListener
 * Description TODO Json for String Respnone
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/4/19 15:19
 */
public class OnResponseStringListener implements Response.Listener<String> {

    private Context mContext;

    private HtResponseListener<String> listener;

    public OnResponseStringListener(Context context, HtResponseListener<String> listener) {
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public void onResponse(String response) {
        try {
            if (this.listener != null) {
                this.listener.onSuccess(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (this.listener != null) {
                this.listener.onFail(mContext.getResources().getString(R.string.errorMsg), null);
            }
        }
    }
}
