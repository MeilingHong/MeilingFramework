package com.meiling.framework.common_util.gson;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
}
