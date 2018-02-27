package com.wiiee.core.platform.util;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by bill.wang on 2/27/18
 */
public abstract class BeanUtil {
    public static void rootLevelStringFieldsTrimToNull(Object bean) {
        if (bean == null) {
            return;
        }

        Field[] fields = bean.getClass().getDeclaredFields();
        if (fields == null) {
            return;
        }

        for (Field f : fields) {
            if (f.getType().isPrimitive()) {
                continue;
            }

            if (f.getType().equals(String.class)) {
                try {
                    f.setAccessible(true);
                    String value = (String) f.get(bean);
                    if (value != null) {
                        value = StringUtils.trimWhitespace(value);
                        f.set(bean, value.equals("") ? null : value);
                    }
                } catch (IllegalAccessException e) {
                }
            }
        }
    }
}
