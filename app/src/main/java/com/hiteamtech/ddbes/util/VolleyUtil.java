package com.hiteamtech.ddbes.util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.hiteamtech.ddbes.R;
import com.hiteamtech.ddbes.request.HiTeamBiDSSLHttpsStack;
import com.hiteamtech.ddbes.request.HiTeamSSLHttpsStack;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Hashtable;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * ClassName: VolleyUtil
 * Description: VolleyUtil
 * author junli Lee mingyangnc@163.com
 * date 2015/11/10 13:53
 */
public class VolleyUtil {

    private static RequestQueue mRequestQueue;


    private VolleyUtil() {
    }

    public static RequestQueue getVolleyRequestQueue(Context context) {
        if (mRequestQueue == null) {

            // Https SSL 单向验证配置
//            HurlStack stack = new HiTeamSSLHttpsStack(context);

            // Https SSL 双向验证配置
//            HurlStack stack = new HiTeamBiDSSLHttpsStack(context);
//            mRequestQueue = Volley.newRequestQueue(context, stack, ConstanUtil.DEFAULT_DISK_USAGE_BYTES);

            mRequestQueue = Volley.newRequestQueue(context, ConstanUtil.DEFAULT_DISK_USAGE_BYTES);
        }
        return mRequestQueue;
    }

}
