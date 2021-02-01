package com.github.wpyuan.generate.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void set(String dbType) {
        contextHolder.set(dbType);
    }

    public static String get() {
        return contextHolder.get();
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
