package com.wiiee.core.domain.cache;

import com.wiiee.core.platform.util.GsonUtil;

/**
 * Created by wang.na on 2017/7/20.
 */
public abstract class CacheRequest {
    @Override
    public int hashCode(){
        return GsonUtil.toJson(this).hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;

        if (o == null) return false;

        if (getClass() != o.getClass()) return false;

        return GsonUtil.toJson(o).equals(GsonUtil.toJson(this));
    }
}
