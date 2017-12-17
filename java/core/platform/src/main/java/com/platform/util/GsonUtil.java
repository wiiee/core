package com.platform.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.List;

/**
 * Created by wang.na on 2017/7/4.
 */
public abstract class GsonUtil {
    private static Gson _gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create();
    private static Gson _prettyGson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object object) {
        return _gson.toJson(object);
    }

    public static String toPrettyJson(Object object) {
        return _prettyGson.toJson(object);
    }

    public static String toJson(Object object, List<String> ignoredNames) {
        JsonElement jsonElement = _gson.toJsonTree(object);

        for (String ignoredName : ignoredNames) {
            jsonElement.getAsJsonObject().remove(ignoredName);
        }

        return jsonElement.toString();
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return _gson.fromJson(json, clazz);
    }

    public static String toJson(Object object, ExclusionStrategy exclusionStrategy) {
        Gson gson = new GsonBuilder().setExclusionStrategies(exclusionStrategy).create();
        return gson.toJson(object);
    }
}
