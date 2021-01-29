package com.github.wpyuan.generate.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

//    private static final AtomicInteger counter = new AtomicInteger(-1);

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
        //  轮询
//        int index = counter.getAndIncrement() % 2;
//        if (counter.get() > 9999) {
//            counter.set(-1);
//        }
//        if (index == 0) {
            set("oracle");
            log.info("切换到oracle");
//        }else {
//            set(DBTypeEnum.SLAVE2);
//            log.info("切换到slave2");
//        }
    }
}
