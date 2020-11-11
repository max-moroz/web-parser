package com.max.context;

public class Context {

    public <T> T getBean(Class<T> clazz) {
        T bean = null;

        try {
            bean = clazz.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
