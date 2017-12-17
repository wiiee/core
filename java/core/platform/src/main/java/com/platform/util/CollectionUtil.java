package com.platform.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by wang.na on 2016/11/14.
 */
public abstract class CollectionUtil {
    public static <K, V> Map<K, V> sortByValue(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        return map.entrySet()
                .stream()
                .sorted(comparator)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public static <K, V> Map<K, V> createLruMap(final int maxEntries) {
        return new LinkedHashMap<K, V>(maxEntries) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxEntries;
            }
        };
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static List<Long> intToLong(List<Integer> list) {
        List<Long> result = new ArrayList<>();
        if(!CollectionUtil.isEmpty(list))
        {
            Function<Integer,Long> convertor = item -> Long.parseLong(String.valueOf(item));
            result = list.stream().map(convertor).collect(Collectors.toList());
        }
        return result;
    }

    public static List<Integer> longToInteger(List<Long> list) {
        List<Integer> result = new ArrayList<>();
        if(!CollectionUtil.isEmpty(list))
        {
            Function<Long,Integer> convertor = item -> Integer.parseInt(String.valueOf(item));
            result = list.stream().map(convertor).collect(Collectors.toList());
        }
        return result;
    }
}
