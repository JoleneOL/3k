package org.jolene.threek.service;

import org.jolene.threek.CoreTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jolene
 */
public class AppServiceTest extends CoreTest {

    @Autowired
    private AppService appService;
    @Autowired
    private TimeService timeService;

    @Test
    public void testCurrentSystemConfig() throws Exception {
        System.out.println(appService);
    }

    @Test
    public void timeTest() throws InterruptedException {
        Instant instant = timeService.nowInstant();
        Thread.sleep(500);
        Instant instant2 = timeService.nowInstant();
        assertThat(instant2)
                .isGreaterThan(instant);

        timeService.updateTimeInMS(instant.toEpochMilli());

        Instant instant3 = timeService.nowInstant();
        assertThat(instant3)
                .isEqualTo(instant);

    }
}