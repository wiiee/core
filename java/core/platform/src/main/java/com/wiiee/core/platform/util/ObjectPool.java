package com.wiiee.core.platform.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public abstract class ObjectPool<T> {
    private static final Logger _logger = LoggerFactory.getLogger(ObjectPool.class);

    private static final int DEFAULT_SIZE = 128;
    private Map<T, ObjectWrapper<T, Boolean>> entries;

    private int size;

    public ObjectPool(int size) {
        this.size = size <= 0 ? DEFAULT_SIZE : size;
        init();
    }

    private void init() {
        this.entries = new HashMap<>(size);

        Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        try {
            for (int i = 0; i < size; i++) {
                T instance = type.newInstance();
                entries.put(instance, new ObjectWrapper<>(instance, Boolean.FALSE));
            }
        } catch (Exception ex) {
            _logger.error(ex.getMessage());
        }
    }

    public synchronized T allocate() {
        while (true) {
            Map.Entry<T, ObjectWrapper<T, Boolean>> result = entries.entrySet().stream()
                    .filter(p -> !p.getValue().getTag()).findFirst().orElse(null);

            if (result != null) {
                result.getValue().setTag(Boolean.TRUE);
                return result.getValue().getObject();
            }
        }
    }

    public void free(T key) {
        ObjectWrapper<T, Boolean> entry = entries.get(key);

        if (entry != null) {
            entry.setTag(Boolean.FALSE);
        }
    }
}
