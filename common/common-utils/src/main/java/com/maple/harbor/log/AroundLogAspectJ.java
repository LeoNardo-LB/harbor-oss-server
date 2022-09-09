package com.maple.harbor.log;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter.Feature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.StringJoiner;

/**
 * 日志打印切面类
 */
@Slf4j
@Aspect
@Component
// 优先级最高
@Order(value = Integer.MIN_VALUE)
public class AroundLogAspectJ {

    @Around(value = "@annotation(com.maple.harbor.log.AroundLog)")
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AroundLog aroundLog = signature.getMethod().getAnnotation(AroundLog.class);
        StopWatch stopWatch = new StopWatch();
        Boolean success = true;

        Object result = null;
        try {
            if (aroundLog.printParams()) {
                StringJoiner joiner = new StringJoiner(",");
                for (Object arg : args) {
                    joiner.add(toJsonString(arg));
                }
                StringBuilder sb = new StringBuilder();
                log.info(sb.append("开始调用方法 [").append(signature.getMethod()).append("], 参数JSON: ").append(joiner).toString());
            }
            stopWatch.start();
            result = joinPoint.proceed(args);
            stopWatch.stop();
            return result;
        } catch (Throwable e) {
            success = false;
            if (aroundLog.printException()) {
                StringBuilder sb = new StringBuilder();
                log.error(sb.append("调用方法 [").append(signature.getMethod()).append("] 失败, 异常信息: ").append(e.getMessage()).toString());
            }
            throw e;
        } finally {
            if (aroundLog.printResult()) {
                StringBuilder sb = new StringBuilder();
                log.info(sb.append("调用方法 [").append(signature.getMethod()).append("] 完毕")
                                 .append(", 是否成功?").append(success)
                                 .append(", 耗时: ").append(stopWatch)
                                 .append(", 返回值JSON: ").append(toJsonString(result)).toString());
            }
        }
    }

    private String toJsonString(Object arg) {
        try {
            return JSON.toJSONString(arg);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
