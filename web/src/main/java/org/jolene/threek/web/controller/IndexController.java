package org.jolene.threek.web.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Jolene
 */
@Controller
public class IndexController {

    @RequestMapping(method = RequestMethod.GET, value = {"", "/"}, produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        return "investment/index";
    }

}
