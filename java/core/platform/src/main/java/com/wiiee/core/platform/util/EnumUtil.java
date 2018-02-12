package com.wiiee.core.platform.util;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by wiiee on 2/5/2018.
 */
public abstract class EnumUtil {
    private static final Logger _logger = LoggerFactory.getLogger(EnumUtil.class);

    private static Map<String, List<Pair<String, Object>>> enums;

    static {
        enums = new HashMap<>();
    }

    public static List<Pair<String, Object>> getOptions(String name){
        if(enums.containsKey(name)){
            return enums.get(name);
        }

        List<Pair<String, Object>> pairs = new ArrayList<>();

        try {
            Class<?> e = Class.forName(name);

            Object[] items = e.getEnumConstants();

            Method[] methods = e.getDeclaredMethods();
            Method method = Arrays.stream(methods).filter(o -> o.getName().equals("value")).findAny().orElse(null);

            for(Object item : items){
                pairs.add(new Pair<>(item.toString(), method == null ? item.toString() : method.invoke(item)));
            }
        } catch (Exception ex) {
            _logger.error(ex.getMessage());
        }

        enums.put(name, pairs);

        return pairs;
    }
}
