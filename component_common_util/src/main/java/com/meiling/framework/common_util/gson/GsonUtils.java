package com.meiling.framework.common_util.gson;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonUtils {

	public static <T> T getListFromJSON(String str, Type type) {
		if (!TextUtils.isEmpty(str)) {
			return Gsons.getInstance().fromJson(str, type);
		}
		return null;
	}

	public static <T> Type getType(T t){
		return new TypeToken<T>(){}.getType();
	}

	public static <T> Type getListType(T t){
		return new TypeToken<List<T>>(){}.getType();
	}

	public static <T> T getObjFromJSON(String str, Class<T> cls) {
		if (!TextUtils.isEmpty(str)) {
			return Gsons.getInstance().fromJson(str, cls);
		}
		return null;
	}

	public static <T> List<T> getObjectList(String jsonString,Class<T> cls){
		List<T> list = new ArrayList<T>();
		try {
			JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
			for (JsonElement jsonElement : arry) {
				list.add(Gsons.getInstance().fromJson(jsonElement, cls));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
