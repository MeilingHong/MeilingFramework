package com.meiling.framework.network.convert.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meiling.framework.network.convert.adapter.DoubleDefault0Adapter;
import com.meiling.framework.network.convert.adapter.IntegerDefault0Adapter;
import com.meiling.framework.network.convert.adapter.LongDefault0Adapter;

public class GsonFactory {
    public static Gson build() {
        return new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
                .create();
    }
}
