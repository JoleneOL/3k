/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package org.jolene.threek.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jolene
 */
@Aspect
public class AutoFlush {

    @Pointcut(
            "execution(* save(..))" +
                    "&&target(org.springframework.data.jpa.repository.JpaRepository)")
    public void savePoint() {
    }

    @Around("savePoint()")
    public Object aroundSave(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        if (pjp.getArgs()[0] != null && pjp.getArgs()[0] instanceof Iterable) {
            return pjp.proceed();
        }
        JpaRepository repository = (JpaRepository) pjp.getTarget();
        return repository.saveAndFlush(pjp.getArgs()[0]);
        // stop stopwatch
    }

}
