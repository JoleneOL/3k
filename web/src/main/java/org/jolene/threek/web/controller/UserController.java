package org.jolene.threek.web.controller;

import org.jolene.threek.entity.User;
import org.jolene.threek.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户相关操作
 *
 * @author Jolene
 */
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    public String myProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "user/profile";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/{id}")
    public String profile(Model model, @AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        model.addAttribute("user", userRepository.getOne(id));
        return "user/profile";
    }

}
