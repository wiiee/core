package com.platform.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wang.na on 2016/11/18.
 */
public interface IData<Id extends Serializable> {
    Id getId();
}
