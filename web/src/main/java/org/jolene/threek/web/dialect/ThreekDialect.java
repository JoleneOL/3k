package org.jolene.threek.web.dialect;

import org.jolene.threek.entity.Login;
import org.jolene.threek.entity.User;
import org.jolene.threek.repository.EmailRepository;
import org.jolene.threek.repository.TicketRepository;
import org.jolene.threek.repository.UserRepository;
import org.jolene.threek.service.AppService;
import org.jolene.threek.service.ResourceService;
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
    private UserRepository userRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private AppService appService;
    @Autowired
    private ResourceService resourceService;

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
        map.put("resourceService", resourceService);
        if (processingContext.getContext() != null && processingContext.getContext() instanceof WebContext) {
            // 另外声明几个静态变量 以标识当前登录的身份
            Authentication authentication = AuthUtils.getAuthenticationObject();
            if (authentication.getPrincipal() instanceof Login) {
                Login login = (Login) authentication.getPrincipal();
                map.put("newEmails", emailRepository.findByBelongAndReadFalse(login));

                map.put("isLogin", true);
                if (authentication.getPrincipal() instanceof User) {
                    User user = (User) authentication.getPrincipal();
                    map.put("isEndUser", true);

                    map.put("user", user);
                    map.put("ticketCount", ticketRepository.countByUsedFalseAndClaimant(user));
                    map.put("newUsers", userRepository.findByGuideAndGuideKnowMeFalse(user));

                } else {
                    map.put("isEndUser", false);
                }
            } else {
                map.put("isEndUser", false);
                map.put("isLogin", false);
            }

        }
        return map;
    }

    @Override
    public String getPrefix() {
        return "k3";
    }
}
