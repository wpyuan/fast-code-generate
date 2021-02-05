package com.github.wpyuan.generate.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBContextHolder {
//    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    private static String contextHolder;

    public static void set(String dbType) {
        contextHolder = dbType;
    }

    public static String get() {
        return contextHolder;
    }

    public static void mysql() {
        set("mysql");
        log.info("切换到mysql");
    }

    public static void oracle() {
        set("oracle");
        log.info("切换到oracle");
    }
}
