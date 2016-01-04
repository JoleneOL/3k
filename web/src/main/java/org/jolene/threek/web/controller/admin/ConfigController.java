package org.jolene.threek.web.controller.admin;

import org.jolene.threek.SystemConfig;
import org.jolene.threek.common.exception.ForbiddenException;
import org.jolene.threek.entity.Login;
import org.jolene.threek.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Jolene
 */
@Controller
public class ConfigController {

    @Autowired
    private AppService appService;

    /**
     * 配置页面
     *
     * @return 页面
     */
    @RequestMapping(method = RequestMethod.GET, value = "/config")
    public String index(@AuthenticationPrincipal Login login) {
        // 如果已配置 需要获得ROOT才可以配置
        if (appService.currentSystemConfig().isConfigRequired())
            return "admin/config";
        if (login == null)
            throw new ForbiddenException();
        if (login.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ROOT"))) {
            return "admin/config";
        } else
            throw new ForbiddenException();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/config")
    @Transactional
    public String change(@AuthenticationPrincipal Login login, String title) {
        // 不进行校验 直接投入到系统中去
        SystemConfig systemConfig = appService.currentSystemConfig();
        systemConfig.setConfigRequired(false);

        systemConfig.setTitle(title);

        //保存到系统中
        appService.saveCurrentSystemConfig();

        if (login == null)
            return "redirect:/";
        return "redirect:/config";
    }
}
