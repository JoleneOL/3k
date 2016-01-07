package org.jolene.threek.web.dialect;

import org.jolene.threek.entity.User;
import org.jolene.threek.repository.TicketRepository;
import org.jolene.threek.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;
import org.thymeleaf.processor.IProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Jolene
 */
@Component
public class ThreekDialect extends AbstractDialect implements IExpressionEnhancingDialect {

    private final Set<IProcessor> processors;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private AppService appService;

    @Autowired
    public ThreekDialect(Set<IProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        return processors;
    }

    @Override
    public Map<String, Object> getAdditionalExpressionObjects(IProcessingContext processingContext) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("config", appService.currentSystemConfig());
        if (processingContext.getContext() != null && processingContext.getContext() instanceof WebContext) {
            // 另外声明几个静态变量 以标识当前登录的身份
            Authentication authentication = AuthUtils.getAuthenticationObject();
            if (authentication.getPrincipal() instanceof User) {
                map.put("isEndUser", true);

                map.put("user", authentication.getPrincipal());
                map.put("ticketCount", ticketRepository.countByUsedFalseAndClaimant((User) authentication.getPrincipal()));
            } else {
                map.put("isEndUser", false);
            }
        }
        return map;
    }

    @Override
    public String getPrefix() {
        return "k3";
    }
}
