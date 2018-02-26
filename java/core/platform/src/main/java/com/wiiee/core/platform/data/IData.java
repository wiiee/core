package com.wiiee.core.platform.data;

import java.io.Serializable;

/**
 * Created by wang.na on 2016/11/18.
 */
public interface IData<Id extends Serializable> {
    Id getId();
    boolean isValid();
}
