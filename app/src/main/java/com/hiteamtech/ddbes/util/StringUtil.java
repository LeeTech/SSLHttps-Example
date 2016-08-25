package com.hiteamtech.ddbes.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private final static String UNITS[] = {" bytes", " KB", " MB", " GB",
            " TB"};
    private final static int MODEL = 1024;

    private StringUtil() {
    }

    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    public static boolean isQQNumber(String mobiles) {
        Pattern p = Pattern.compile("^[1-9][0-9]{4,}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isTelephone(String strEmail) {
        String strPattern = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    public static boolean isMobilephone(String mobiles) {
        if (mobiles.startsWith("86") || mobiles.startsWith("+86")) {
            mobiles = mobiles.substring(mobiles.indexOf("86") + 2);
        }
        Pattern p = Pattern.compile("[1][34578]\\d{9}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 获取列表显示的时间 "今天、昨天、前天"
     */
    public static String getListTime(long time) {
        if (time <= 0) {
            return "";
        }
        /** 一天的毫秒数 */
        long daySecond = 24 * 60 * 60 * 1000;
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat format = null;
        String text = null;
        long x = currentTime - time;

        if (3 * daySecond > x && x >= 2 * daySecond) {
            format = new SimpleDateFormat("HH:mm", Locale.CHINA);
            text = "前天 " + format.format(new Date(time));
        } else if (2 * daySecond > x && x >= daySecond) {
            format = new SimpleDateFormat("HH:mm", Locale.CHINA);

            text = "昨天 " + format.format(new Date(time));
        } else if (daySecond > x) {
            format = new SimpleDateFormat("HH:mm", Locale.CHINA);
            text = "今天 " + format.format(new Date(time));
        } else {
            format = new SimpleDateFormat("MM-dd", Locale.CHINA);
            text = format.format(new Date(time));
        }
        return text;
    }


    /**
     * 验证密码 密码格式：最少6位，由数字和字母组成，区分大小写
     *
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isZipCode(String mobiles) {
        Pattern p = Pattern.compile("[0-9]\\d{5}(?!\\d)");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isNotBlankAndEmpty(String str) {
        if (str != null && !str.equals("null") && str.length() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isIp(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        }

        String[] fields = value.split("\\.");
        if (fields.length != 4) {
            return false;
        }

        boolean result = true;
        for (int i = 0; i < fields.length; i++) {
            try {
                int number = Integer.parseInt(fields[i]);
                result &= (number >= 0 && number <= 255);
            } catch (NumberFormatException e) {
                result = false;
            }

            if (!result) {
                break;
            }
        }
        return result;
    }

    public static String toHex(byte[] b) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            int v = b[i];
            builder.append(HEX[(0xF0 & v) >> 4]);
            builder.append(HEX[0x0F & v]);
        }
        return builder.toString();
    }

    public static byte[] hexToBytes(String str) {
        int len = str.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < b.length; i++) {
            int start = i * 2;
            String v = str.substring(start, start + 2);
            try {
                b[i] = (byte) Integer.parseInt(v, 16);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return b;
    }

    public static String strToHourAndMin(int value) {
        String str = "";
        if ((value + "").length() == 1) {
            str = "0" + value;
        } else {
            str = value + "";
        }
        return str;
    }

    public static boolean isEquals(String str1, String str2) {
        boolean result = false;
        if (str1 != null) {
            result = str1.equals(str2);
        } else if (str2 == null) {
            result = true;
        }
        return result;
    }

    public static String toSqliteStr(String str) {
        String result = null;
        if (str != null) {
            result = str.replaceAll("'", "''");
            result = result.replaceAll("%", "/%");
            result = result.replaceAll("_", "/_");
        }
        return result;
    }

    public static String toFileSize(long size) {
        int lastIndex = 0;
        long value = size;
        long last = 0L;

        for (int i = 0; i < UNITS.length; i++) {
            long newValue = value / MODEL;
            if (newValue <= 0) {
                break;
            }

            last = value % MODEL;
            value = newValue;
            lastIndex = i + 1;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(value);
        if (last > 0L) {
            float f = (last + 0.0f) / (MODEL + 0.0f);
            int i = Math.round(f * 100);
            if (i > 0) {
                builder.append(".").append(i);
            }
        }
        builder.append(UNITS[lastIndex]);
        return builder.toString();
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateString(String formatdate) {
        SimpleDateFormat fml = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        long unixlong = 0;
        String date = "";
        unixlong = Long.parseLong(formatdate);
        System.out.println("String转换Long错误，请确认数据可以转换");
        date = fml.format(unixlong);
        System.out.println("String转换Long错误，请确认数据可以转换");
        return date;
    }

    public static int nullSafeStringComparator(final String one,
                                               final String two) {
        if (one == null ^ two == null) {
            return (one == null) ? -1 : 1;
        }

        if (one == null && two == null) {
            return 0;
        }
        return one.compareToIgnoreCase(two);
    }

    public static byte[] toBytes(String str) {
        int len = str.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < b.length; i++) {
            int start = i * 2;
            String v = str.substring(start, start + 2);
            try {
                b[i] = (byte) Integer.parseInt(v, 16);
            } catch (NumberFormatException e) {
                e.getMessage();
                return null;
            }
        }
        return b;
    }

    public static String[] split(String string, String delimiters) {
        StringTokenizer st = new StringTokenizer(string, delimiters);
        String[] fields = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++) {
            String token = st.nextToken();
            fields[i] = token;
        }

        return fields;
    }

    public static int versionCompare(String version1, String version2) {
        if (version1 == null && version2 == null) {
            return 0;
        } else if (version1 == null) {
            return -1;
        } else if (version2 == null) {
            return 1;
        } else {
            String[] fields1 = split(version1, ".");
            String[] fields2 = split(version2, ".");
            int loop = Math.min(fields1.length, fields2.length);

            int result = 0;
            for (int i = 0; i < loop; i++) {
                result = fields1[i].compareTo(fields2[i]);
                if (result != 0) {
                    return result;
                }
            }

            return fields1.length - fields2.length;
        }
    }

    public static String formatGB(String format, long usedSize,
                                  long availableSize) {
        return String.format(format, usedSize / (1024.0 * 1024.0 * 1024.0),
                availableSize / (1024.0 * 1024.0 * 1024.0));
    }

    public static String formatJsonString(String jsonObj) {
        if (!jsonObj.startsWith("{"))
            jsonObj = jsonObj.substring(1);

        return jsonObj;
    }

    public static boolean isNullJsonObj(JSONObject json, String name) {
        return json != null ? json.isNull(name) : false;
    }

    public static String unEncode(String data) {
        if (null == data || "".equals(data))
            return data;
        data = data.replaceAll("&quot;", "\"");
        data = data.replaceAll("&lt;", "<");
        data = data.replaceAll("&gt;", ">");
        data = data.replaceAll("&#92;", "\\\\");
        data = data.replaceAll("&apos;", "'");
        data = data.replaceAll("&amp;", "&");
        return data;
    }

    public String getMd5Str(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            password = bytesToString(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }

    private String bytesToString(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查字符串是否为null或者为空
     *
     * @param s
     * @return
     */
    public static boolean emptyOrNull(String s) {
        if (s == null) {
            return true;
        }
        if ("".equals(s)) {
            return true;
        }
        return false;
    }

    /**
     * 转换为时间戳
     *
     * @param timelong
     * @return
     */
    public static String getStringTimeForLongtime(long timelong) {
        if (timelong < 10) {
            return "";
        }
        Date date = new Date(timelong);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    /**
     * 格式化文件大小
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        if (size <= 0) {
            return 0 + "B";
        }
        double kiloByte = size / 1024;
        if (kiloByte <= 1) {
            // return size + "Byte(s)";
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "G";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "T";
    }

    /**
     * 将string转为int ，异常时返回-1
     *
     * @param s
     * @return
     */
    public static int toInt(String s) {
        int i = 0;
        try {
            i = Integer.parseInt(s);
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }
}
