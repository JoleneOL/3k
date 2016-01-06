package org.jolene.threek.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Jolene
 */
@Controller
public class InvestmentController {

    @RequestMapping(method = RequestMethod.POST, value = "/provides")
    public String doProvide() {
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accepts")
    public String doAccept() {
        return "redirect:/";
    }

}
