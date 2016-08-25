package com.hiteamtech.ddbes.request;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * ClassName: HtHttpHeaderParser
 * Description: 处理请求头
 * author junli Lee mingyangnc@163.com
 * date 2015/11/9 20:08
 */
public class HtHttpHeaderParser extends HttpHeaderParser {

    public static Cache.Entry parseCacheHeaders(NetworkResponse response, long cacheTime) {

        Cache.Entry entry = parseCacheHeaders(response);
        long now = System.currentTimeMillis();
        long softExpire = now + cacheTime;
        entry.softTtl = softExpire;
        entry.ttl = entry.softTtl;
        return entry;
    }
}
