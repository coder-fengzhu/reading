package com.fengzhu.reading.utils;

public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    //从jwt拦截器中获取当前的id
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    //获取后通过thread带到想要使用的地方使用
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    //删除当前thread的信息
    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
