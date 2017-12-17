package com.platform.data;

import com.platform.constant.Symbol;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiiee on 2/28/2017.
 */
public class BaseInfoComposition<T extends BaseData<String>> extends BaseData<String> {
    private Map<Integer, T> infos;
    private int current;

    public BaseInfoComposition(String id) {
        super(id);
        infos = new HashMap<>();
    }

    public BaseInfoComposition() {
        infos = new HashMap<>();
    }

    public String add(T info) {
        current++;
        //info.setCreated(new Date());
        infos.put(current, info);
        return this.getId() + Symbol.UNDERSCORE + current;
    }

    public T getInfo(String infoId) {
        int index = Integer.parseInt(infoId.split(Symbol.UNDERSCORE)[1]);
        return infos.get(index);
    }
}
