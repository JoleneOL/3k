package org.jolene.threek.web.controller.admin;

import org.jolene.threek.SystemConfig;
import org.jolene.threek.common.exception.ForbiddenException;
import org.jolene.threek.entity.Login;
import org.jolene.threek.service.AppService;
import org.jolene.threek.support.InterestReward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
    public String index(@AuthenticationPrincipal Login login, Model model, HttpServletRequest request) {
        model.addAttribute("saveResult", request.getSession().getAttribute("saveResult"));
        model.addAttribute("saveMsg", request.getSession().getAttribute("saveMsg"));
        request.getSession().setAttribute("saveResult", null);
        request.getSession().setAttribute("saveMsg", null);
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

    /**
     * 保存配置信息
     *
     * @param login
     * @param requestConfig
     * @param tags
     * @param rewardSerialization
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/config")
    @Transactional
    public String change(@AuthenticationPrincipal Login login,
                         SystemConfig requestConfig,
                         String tags,
                         String rewardSerialization,
                         HttpServletRequest request
    ) {
        // 不进行校验 直接投入到系统中去
        SystemConfig systemConfig = appService.currentSystemConfig();

        if (!systemConfig.isConfigRequired() && login == null) {
            return "redirect:/";
        }

        if (!systemConfig.isConfigRequired() && login.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ROOT"))) {
            return "redirect:/";
        }

        systemConfig.setConfigRequired(false);

        systemConfig.setTitle(requestConfig.getTitle());
        systemConfig.setUrl(requestConfig.getUrl());
        systemConfig.setWelcomeMessage(requestConfig.getWelcomeMessage());
        systemConfig.setWelcomeFeatures(tags.split(","));
        systemConfig.setUserHelpMessage(requestConfig.getUserHelpMessage());
        systemConfig.setIndexTopNotice(requestConfig.getIndexTopNotice());
        systemConfig.setIndexBottomNotice(requestConfig.getIndexBottomNotice());
        systemConfig.setStock(requestConfig.getStock());
        systemConfig.setMaxOrders(requestConfig.getMaxOrders());
        systemConfig.setMaxLots(requestConfig.getMaxLots());
        systemConfig.setQueueDays(requestConfig.getQueueDays());
        systemConfig.setRate(requestConfig.getRate());
        systemConfig.setMaxOperateHours(requestConfig.getMaxOperateHours());
        systemConfig.setOnlyInvite(requestConfig.isOnlyInvite());
        systemConfig.setRegWelcomeMessage(requestConfig.getRegWelcomeMessage());
        systemConfig.setInspectStartDayOfMonth(requestConfig.getInspectStartDayOfMonth());
        systemConfig.setInspectEndDayOfMonth(requestConfig.getInspectEndDayOfMonth());
        String[] interestRewardsInfo = rewardSerialization.split("\\|");
        Map<Integer, InterestReward> interestRewardMap = new HashMap<>();

        for (int i = 0; i < interestRewardsInfo.length; i++) {
            String[] rewordDetail = interestRewardsInfo[i].split(",");
            InterestReward interestReward = new InterestReward();
            interestReward.setRate(Double.parseDouble(rewordDetail[0]));
            interestReward.setReach(Integer.parseInt(rewordDetail[1]));
            interestReward.setMax(Integer.parseInt(rewordDetail[2]));
            interestRewardMap.put(i + 1, interestReward);
        }
        systemConfig.setLeaderStopRecommends(requestConfig.getLeaderStopRecommends());
        systemConfig.setLeaderStopMembers(requestConfig.getLeaderStopMembers());
        systemConfig.setLeaderStopInvests(requestConfig.getLeaderStopInvests());
        systemConfig.setLeaderRate(requestConfig.getLeaderRate());

        //保存到系统中
        appService.saveCurrentSystemConfig();

        request.getSession().setAttribute("saveResult", 1);
        request.getSession().setAttribute("saveMsg", "保存成功");


        return "redirect:/config";
    }
}
