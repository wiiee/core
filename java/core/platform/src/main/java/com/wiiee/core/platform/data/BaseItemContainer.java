package com.wiiee.core.platform.data;

import com.wiiee.core.platform.constant.Symbol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiiee on 2/28/2017.
 */
public class BaseItemContainer<T extends BaseData<String>, Id extends Serializable> extends BaseData<Id> {
    private Map<Integer, T> items;
    private int current;

    public BaseItemContainer(Id id) {
        super(id);
        items = new HashMap<>();
    }

    public BaseItemContainer() {
        items = new HashMap<>();
    }

    public synchronized String add(T item) {
        current++;
        String id = this.getId() + Symbol.UNDERSCORE + current;
        item.setId(id);
        items.put(current, item);

        return id;
    }

    public T get(String itemId) {
        int index = Integer.parseInt(itemId.split(Symbol.UNDERSCORE)[1]);
        return items.get(index);
    }
}
