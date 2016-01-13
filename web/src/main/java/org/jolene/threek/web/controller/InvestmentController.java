package org.jolene.threek.web.controller;

import org.jolene.threek.entity.Login;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Jolene
 */
@Controller
public class InvestmentController {

    @RequestMapping(method = RequestMethod.POST, value = "/provides")
    public String doProvide(
            @AuthenticationPrincipal Login login
    ) {

        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accepts")
    public String doAccept(
            @AuthenticationPrincipal Login login
    ) {
        // TODO: 1/13/16 判断是否登录及是否提供过帮助
        if (login == null) {

        }
        return "redirect:/";
    }

}
