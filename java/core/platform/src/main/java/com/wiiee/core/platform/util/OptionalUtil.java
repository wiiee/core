package com.wiiee.core.platform.util;

import java.util.Optional;

/**
 * Created by bill.wang on 3/10/18
 */
public abstract class OptionalUtil {
    public static <T> Optional<T> getOptional(T obj){
        if(obj == null){
            return Optional.empty();
        }

        return Optional.of(obj);
    }
}
