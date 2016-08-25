package com.hiteamtech.ddbes.util;

import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * 
* @ClassName: JsonUtil 
* @Description: Map转JSON 
* @author liuqi qiliu_17173@cyou-inc.com
* @date 2014-8-4 上午11:51:19
 */
public class JsonUtil {

	public static Map<String, Object> json2map(String jsonStr) {
		Map<String, Object> map = null;
		try {
			map = getGson().fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static String toJson(Object obj) {
		String jsonstr = null;
		try {
			jsonstr = getGson().toJson(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonstr;
	}

	/**
	 * 构建默认的Gson处理器
	 * 
	 * @return
	 */
	public static Gson getGson() {

		Gson gson = new GsonBuilder().enableComplexMapKeySerialization() // 支持Map的key为复杂对象的形式
				.create();

		return gson;
	}
}
