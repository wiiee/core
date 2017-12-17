package com.platform.context;

import org.springframework.core.NamedThreadLocal;

/**
 * Created by wang.na on 2017/7/4.
 */
public abstract class BaseContextRepository implements IContextRepository {
    private ThreadLocal<IContext> contextHolder;

    public BaseContextRepository(String name){
        contextHolder = new NamedThreadLocal<>(name);
    }

    @Override
    public IContext getCurrent() {
        return contextHolder.get();
    }

    public void setContext(IContext context){
        contextHolder.set(context);
    }

    @Override
    public <T extends IContext> T getContext(Class<T> clazz) {
        IContext context = getCurrent();

        if(context != null && context.getClass().equals(clazz)){
            return (T)context;
        }

        return null;
    }
}
