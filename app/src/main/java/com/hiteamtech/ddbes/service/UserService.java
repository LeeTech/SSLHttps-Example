package com.hiteamtech.ddbes.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.hiteamtech.ddbes.bean.UserBean;
import com.hiteamtech.ddbes.request.HiTeamJsonObjectRequest;
import com.hiteamtech.ddbes.request.HiTeamRequest;
import com.hiteamtech.ddbes.request.HtResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: UserService
 * Description TODO 用户服务
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/4/19 15:34
 */
public class UserService {

    public static final String LOGIN = "/user/login";

    public static final String REGISTER = "/user/register";

    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    /**
     * 注册
     *
     * @param context
     * @param queue
     * @param phone
     * @param password
     * @param listener
     * @return
     */
    public static HiTeamJsonObjectRequest register(Context context, RequestQueue queue, String phone, String password,
                                                   HtResponseListener<String> listener) {
        HiTeamRequest request = new HiTeamRequest();
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);
        request.setParams(params);
        return request.send(context, Request.Method.POST, REGISTER, queue, listener, false);
    }

    /**
     * 登录
     *
     * @param context
     * @param queue
     * @param phone
     * @param password
     * @param listener
     * @return
     */
    public static HiTeamJsonObjectRequest login(Context context, RequestQueue queue, String phone, String password,
                                                HtResponseListener<UserBean> listener) {
        HiTeamRequest request = new HiTeamRequest();
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);
        request.setParams(params);
        return request.send(context, Request.Method.POST, LOGIN, queue, listener, UserBean.class, false);
    }
}
