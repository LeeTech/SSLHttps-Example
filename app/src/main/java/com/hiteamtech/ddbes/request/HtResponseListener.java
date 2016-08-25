package com.hiteamtech.ddbes.request;

/**
 * ClassName: HtResponseListener
 * Description: 请求回调
 * author junli Lee mingyangnc@163.com
 * date 2015/11/9 17:45
 */
public interface HtResponseListener<T> {

    public void onStart();

    public void onSuccess(T t);

    public void onFail(String errorMsg, T cach);
}
