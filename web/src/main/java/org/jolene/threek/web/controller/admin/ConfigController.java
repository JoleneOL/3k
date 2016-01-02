package org.jolene.threek.web.controller.admin;

import org.jolene.threek.entity.Login;
import org.jolene.threek.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.ForbiddenException;

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
}
