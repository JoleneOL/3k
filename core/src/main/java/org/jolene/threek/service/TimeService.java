package org.jolene.threek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Jolene
 */
@Service
public class TimeService {
    @Autowired
    private Environment environment;

    /**
     * 模拟的当前时间
     */
    private Long timeInMS;

    /**
     * 更新现在时间,只可以在测试中使用
     *
     * @param ms 格林威治毫秒 如果为<code>null</code>恢复为系统时间
     * @throws IllegalStateException 如果不在测试环境中
     */
    public void updateTimeInMS(Long ms) {
        if (!environment.acceptsProfiles("test"))
            throw new IllegalStateException("can not call in NO-test environment.");

        timeInMS = ms;
    }

    public LocalDateTime nowDateTime() {
        return LocalDateTime.ofInstant(nowInstant(), nowClock().getZone());
    }

    public LocalDate nowDate() {
        return LocalDate.from(nowDateTime());
    }

    public Clock nowClock() {
        return Clock.systemDefaultZone();
    }

    public Instant nowInstant() {
        if (environment.acceptsProfiles("test")) {
            if (timeInMS != null)
                return Instant.ofEpochMilli(timeInMS);
        }
        return Instant.now();
    }

    public LocalTime nowTime() {
        return LocalTime.from(nowDateTime());
    }

}
