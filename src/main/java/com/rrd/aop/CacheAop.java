package com.rrd.aop;

import com.rrd.dao.CustomDao;
import com.rrd.plugin.JedisHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xinghb on 2017/10/27.
 */
@Component
@Aspect
public class CacheAop {

    @Autowired
    private JedisHelper jedisHelper;

    @Autowired
    private CustomDao customDao;

    @Pointcut("execution(* com.rrd.service.*.*(..)) && @annotation(com.rrd.annotation.RefushRedis)")
    public void pointMethod() {
    }

    @Around("pointMethod()")
    public Object Interceptor(ProceedingJoinPoint point) throws Throwable {
        return point.proceed(); //执行方法
    }
}
