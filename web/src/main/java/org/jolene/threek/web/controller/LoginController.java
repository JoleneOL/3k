package org.jolene.threek.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * @author Jolene
 */
@Controller
public class LoginController {

    private static final Log log = LogFactory.getLog(LoginController.class);

    @RequestMapping(method = RequestMethod.GET,value = "/login")
    public String login(Locale locale) {
        log.info(locale);
        return "signin";
    }

}
