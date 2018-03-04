package com.wiiee.core.platform.data;

import com.wiiee.core.platform.constant.Symbol;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wiiee on 2/28/2017.
 */
public class BaseItemContainer<T extends BaseData<String>> extends BaseData<String> {
    private Map<Integer, T> items;
    private int current;

    public BaseItemContainer(String id) {
        super(id);
        items = new HashMap<>();
    }

    public BaseItemContainer() {
        items = new HashMap<>();
    }

    public synchronized String addItem(T item) {
        current++;
        String id = this.getId() + Symbol.UNDERSCORE + current;
        item.setId(id);
        items.put(current, item);

        return id;
    }

    public void updateItem(T item) {
        items.put(getItemId(item.getId()), item);
    }

    public List<T> getItems() {
        return items.values().stream().collect(Collectors.toList());
    }

    public T getItem(String itemId) {
        int index = Integer.parseInt(itemId.split(Symbol.UNDERSCORE)[1]);
        return items.get(index);
    }

    public void removeItem(String itemId) {
        items.remove(getItemId(itemId));
    }

    public static int getItemId(String itemId) {
        return Integer.parseInt(itemId.split(Symbol.UNDERSCORE)[1]);
    }

    public static String getContainerId(String itemId) {
        return itemId.split(Symbol.UNDERSCORE)[0];
    }
}
