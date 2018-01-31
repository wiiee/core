package com.wiiee.core.platform.data;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by wang.na on 2016/11/7.
 */
public abstract class BaseData<TId extends Serializable> implements IData<TId> {
    @Id
    private TId id;

    public BaseData(TId id){
        this.id = id;
    }

    public BaseData(){

    }

    @Override
    public TId getId(){
        return id;
    }

    @Override
    public void setId(TId id) {
        if(id != null){
            //ToDo: throw exception
        }

        this.id = id;
    }
}
