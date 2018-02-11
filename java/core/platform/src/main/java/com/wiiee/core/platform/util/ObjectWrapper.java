package com.wiiee.core.platform.util;

public class ObjectWrapper<T, Tag> {
    private final T ob;
    private Tag tag;

    public ObjectWrapper(T ob, Tag tag) {
        this.ob = ob;
        this.tag = tag;
    }

    public T getObject() {
        return ob;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
