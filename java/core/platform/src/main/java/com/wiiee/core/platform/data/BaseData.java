package com.wiiee.core.platform.data;

import java.io.Serializable;

/**
 * Created by wang.na on 2016/11/7.
 */
public abstract class BaseData<TId extends Serializable> implements IData<TId>, Serializable {
    private TId id;

    public BaseData(TId id) {
        this.id = id;
    }

    public BaseData() {
    }

    @Override
    public TId getId() {
        return id;
    }

    public void setId(TId id) {
        if (id != null) {
            //ToDo: throw exception
        }

        this.id = id;
    }

    @Override
    public boolean equals(Object o){
        if(this.getClass().isInstance(o)){
            BaseData other = (BaseData)o;

            return other.getId().equals(id);
        }

        return false;
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }

}
