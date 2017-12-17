package com.platform.context;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface IContextRepository {
    IContext getCurrent();

    void setContext(IContext context);

    <T extends IContext> T getContext(Class<T> clazz);
}
