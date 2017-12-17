package com.platform.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wiiee on 3/25/2017.
 */
public class LinkedList<T extends Serializable> {
    private T id;
    private List<LinkedList> next;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public List<LinkedList> getNext() {
        return next;
    }

    public void setNext(List<LinkedList> next) {
        this.next = next;
    }
}
