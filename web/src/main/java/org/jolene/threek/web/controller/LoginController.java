package org.jolene.threek.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jolene.threek.entity.User;
import org.jolene.threek.repository.UserRepository;
import org.jolene.threek.web.MVCUtils;
import org.jolene.threek.web.model.RegisterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

/**
 * @author Jolene
 */
@Controller
public class LoginController {

    private static final Log log = LogFactory.getLog(LoginController.class);
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login(Locale locale) {
        log.info(locale);
        return "signin";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    @Transactional
    public String register(RegisterInfo info, RedirectAttributes model) {

        if (!info.getPassword().equalsIgnoreCase(info.getPassword2())) {
            model.addFlashAttribute("info", info);
            MVCUtils.addDangerMessage(model, "您输入的密码并不一致.");
            return "redirect:/register";
        }

        User guide = userRepository.findByCode(info.getCode());
        if (guide == null) {
            model.addFlashAttribute("info", info);
            MVCUtils.addDangerMessage(model, "您输入的邀请码无效.");
            return "redirect:/register";
        }

        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public String registerPage() {
        return "user/signup";
    }

}
