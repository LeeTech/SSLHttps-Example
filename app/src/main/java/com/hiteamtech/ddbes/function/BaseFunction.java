package com.hiteamtech.ddbes.function;

import android.text.TextUtils;

import com.hiteamtech.ddbes.util.ConstanUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author lijun mingyangnc@163.com
 * @ClassName: BaseFunction
 * @Description: 对外服务器基础类
 * @date 2015-7-23 下午3:21:52
 */
public abstract class BaseFunction {
    public static final String succ = "100000";
    private String code = "";
    private String msg = "";
    private String count = "";
    private boolean isSucc = false;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSucc() {
        return isSucc;
    }

    public void setSucc(boolean isSucc) {
        this.isSucc = isSucc;
    }


    /**
     * @Title: preParseServerJson
     * @Description: (提前预处理返回回来的json, 处理好code和message)
     * @param: @param paramString
     * @param: @return
     * @param: @throws JSONException 设定文件
     * @return: String 返回类型
     * @date: 2014-11-17 上午11:06:11
     */
    public JSONObject preParseServerJson(String paramString)
            throws JSONException {
        String code;
        if (TextUtils.isEmpty(paramString)) {
            return null;
        } else {
            JSONObject jsonObjectCheck = new JSONObject(paramString);
            if (jsonObjectCheck.has(ConstanUtil.Main.CODE)) {
                code = jsonObjectCheck.optString(ConstanUtil.Main.CODE);
            } else {
                return jsonObjectCheck;
            }
            setCode(code);
            setMsg(jsonObjectCheck.optString(ConstanUtil.Main.MESSAGE));
            return jsonObjectCheck;
        }
    }

}
