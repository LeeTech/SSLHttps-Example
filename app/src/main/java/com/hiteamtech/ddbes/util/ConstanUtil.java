package com.hiteamtech.ddbes.util;

/**
 * ClassName: ConstanUtil
 * Description: 全局设置
 * author junli Lee mingyangnc@163.com
 * date 2015/11/9 19:03
 */
public class ConstanUtil {

    /**
     * cache time
     */
    public static final Long CACHE_TIME = 30 * 60 * 1000L;

    /**
     * base url
     */
    public static final String BASE_URL = "https://ddbes.hiteamtech.com:8443/ddbes";


    public static final int DEFAULT_DISK_USAGE_BYTES = 20 * 1024 * 1024;

    public static class Main {
        public static final String CODE = "code";
        public static final String ROWS = "rows";
        public static final String MESSAGE = "message";
    }

    public static class USER {
        public static final String USERID = "userid";
        public static final String TOKENS = "tokens";
    }

}
