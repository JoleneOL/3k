package org.jolene.threek.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Jolene
 */
@Controller
public class LoginController {

    @RequestMapping(method = RequestMethod.GET,value = "/login")
    public String login(){
        return "signin";
    }

}
