package org.jolene.threek.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jolene.threek.exception.ConfigRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Jolene
 */
@ControllerAdvice
public class ControllerEnhance {

    private static final Log log = LogFactory.getLog(ControllerEnhance.class);

    @ExceptionHandler(value = ConfigRequiredException.class)
    public String sawConfigRequiredException() {
        log.info("System require config, right now.");
        return "redirect:/config";
    }

}
