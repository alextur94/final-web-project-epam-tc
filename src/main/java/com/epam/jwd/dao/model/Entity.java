package com.epam.jwd.dao.model;

public abstract class Entity<T> {
    protected T id;

    public Entity() {
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
