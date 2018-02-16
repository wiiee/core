package com.wiiee.core.platform.util.tree;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node<T extends Serializable> {
    private T data = null;

    private List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;

    public Node(T data) {
        this.data = data;
    }

    public Node<T> addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public void addChildren(List<Node<T>> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getParent() {
        return parent;
    }

    public Node<T> getRoot() {
        if (parent == null) {
            return this;
        }

        return parent.getRoot();
    }

    public void deleteNode() {
        if (parent != null) {
            int index = this.parent.getChildren().indexOf(this);
            this.parent.getChildren().remove(this);
            for (Node<T> each : getChildren()) {
                each.setParent(this.parent);
            }
            this.parent.getChildren().addAll(index, this.getChildren());
        } else {
            deleteRootNode();
        }
        this.getChildren().clear();
    }

    public Node<T> deleteRootNode() {
        if (parent != null) {
            throw new IllegalStateException("deleteRootNode not called on root");
        }
        Node<T> newParent = null;
        if (!getChildren().isEmpty()) {
            newParent = getChildren().get(0);
            newParent.setParent(null);
            getChildren().remove(0);
            for (Node<T> each : getChildren()) {
                each.setParent(newParent);
            }
            newParent.getChildren().addAll(getChildren());
        }
        this.getChildren().clear();
        return newParent;
    }

    //打印整棵树
    public void print(String appender){
        printTree(getRoot(), appender);
    }

    private void printTree(Node<T> node, String appender) {
        System.out.println(appender + node.getData());
        node.getChildren().forEach(each -> printTree(each, appender + appender));
    }

    //找到所有后代
    public void buildDescendants(Node<T> node, T data, Set<T> result) {
        //ToDo: throw exception
        if(result == null){
            return;
        }

        if(node == null){
            node = getRoot();
        }

        if (node.getData().equals(data)) {
            addDescendants(node, result);
        } else {
            node.getChildren().forEach(o -> buildDescendants(o, data, result));
        }
    }

    //找到儿子
    public void buildChildren(Node<T> node, T data, Set<T> result) {
        //ToDo: throw exception
        if(result == null){
            return;
        }

        if(node == null){
            node = getRoot();
        }

        if (node.getData().equals(data)) {
            node.getChildren().forEach(o -> result.add(o.getData()));
        } else {
            node.getChildren().forEach(o -> buildChildren(o, data, result));
        }
    }

    //获取高度，从叶子节点往上遍历
    public int getHeight(Set<Node<T>> leaves, T data){
        if(CollectionUtils.isEmpty(leaves)){
            leaves = new HashSet<>();
            buildLeaves(getRoot(), leaves);
        }

        int height = 0;

        for(Node<T> node : leaves){
            height = Math.max(height, getHeight(node, data));
        };

        return height;
    }

    private int getHeight(Node<T> leaf, T data){
        int height = 0;

        Node<T> parent = leaf.getParent();

        while(parent != null){
            height++;

            if(parent.getData().equals(data)){
                return height;
            }

            parent = parent.getParent();
        }

        return 0;
    }

    //获取叶子节点
    public void buildLeaves(Node<T> node, Set<Node<T>> result){
        //ToDo: throw exception
        if(result == null){
            return;
        }

        if(node == null){
            node = getRoot();
        }

        if(CollectionUtils.isEmpty(node.getChildren())){
            result.add(node);
        }
        else{
            node.getChildren().forEach(o -> buildLeaves(o, result));
        }
    }

    private void addDescendants(Node<T> node, Set<T> result) {
        if (result == null) {
            return;
        }

        result.add(node.getData());
        node.getChildren().forEach(o -> addDescendants(o, result));
    }
}
