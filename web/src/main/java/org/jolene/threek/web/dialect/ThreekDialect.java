package org.jolene.threek.web.dialect;

import org.jolene.threek.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jolene
 */
@Component
public class ThreekDialect extends AbstractDialect implements IExpressionEnhancingDialect {
    @Autowired
    private AppService appService;

    @Override
    public Map<String, Object> getAdditionalExpressionObjects(IProcessingContext processingContext) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("config", appService.currentSystemConfig());
        return map;
    }

    @Override
    public String getPrefix() {
        return "3k";
    }
}
