package com.example.queuedemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * Created by gavin on 2017. 1. 15..
 */
public class CustomAsyncExceptionhandler implements AsyncUncaughtExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomAsyncExceptionhandler.class);
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.error("AsyncUncaughtException-Exception message - " + throwable.getMessage());
        log.error("AsyncUncaughtException-Method name - " + method.getName());
        for (Object param : objects) {
            log.error("AsyncUncaughtException-Parameter value - " + param);
        }
    }
}
