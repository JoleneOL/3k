package org.jolene.threek.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jolene.threek.entity.User;
import org.jolene.threek.event.UserRegisterEvent;
import org.jolene.threek.repository.LoginRepository;
import org.jolene.threek.repository.UserRepository;
import org.jolene.threek.service.LoginService;
import org.jolene.threek.web.MVCUtils;
import org.jolene.threek.web.model.RegisterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author Jolene
 */
@Controller
public class LoginController {

    private static final Log log = LogFactory.getLog(LoginController.class);
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginService loginService;
    @Autowired
    private LoginRepository loginRepository;

    private SecurityContextRepository httpSessionSecurityContextRepository = new HttpSessionSecurityContextRepository();

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login(Locale locale) {
        log.info(locale);
        return "signin";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    @Transactional
    public String register(RegisterInfo info, RedirectAttributes model, HttpServletRequest request,HttpServletResponse response) {

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
        if (loginRepository.findByUsername(info.getMobile()) != null) {
            model.addFlashAttribute("info", info);
            MVCUtils.addDangerMessage(model, "这个手机号码已经被人注册.");
            return "redirect:/register";
        }

        User user = new User();
        user.setGuide(guide);
        user.setUsername(info.getMobile());
        loginService.changeLoginWithRawPassword(user, info.getPassword());

        HttpRequestResponseHolder holder = new HttpRequestResponseHolder(request, response);
        SecurityContext context = httpSessionSecurityContextRepository.loadContext(holder);
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user,info.getPassword(),user.getAuthorities()));
        httpSessionSecurityContextRepository.saveContext(context, holder.getRequest(), holder.getResponse());

        applicationEventPublisher.publishEvent(new UserRegisterEvent(user));
        MVCUtils.addSuccessMessage(model, "恭喜,您已经成功注册!");

        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public String registerPage() {
        return "user/signup";
    }

}
