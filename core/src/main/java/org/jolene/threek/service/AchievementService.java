package org.jolene.threek.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jolene.threek.event.UserRegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * 业绩
 *
 * @author Jolene
 */
@Service
public class AchievementService {

    private static final Log log = LogFactory.getLog(AchievementService.class);

    @EventListener
    public void register(UserRegisterEvent event) {

        log.info(event);

// You can define a non-void return type for any method annotated with @EventListener. If you return a non
// null value as the result of the processing of a particular event, we’ll send that result as a new event for you.


    }

}
