package com.leer.lib.event;

import org.jetbrains.annotations.NotNull;

public class Event<T> {

    private int action;
    private T data;

    public Event(int action) {
        this.action = action;
    }

    public Event(int action, T data) {
        this.action = action;
        this.data = data;
    }


    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @NotNull
    @Override
    public String toString() {
        return "Event[action:" + action + ",data:" + data + "]";
    }
}