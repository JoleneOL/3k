package org.jolene.threek.web.dialect;

import org.jolene.threek.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;
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
        return map;
    }

    @Override
    public String getPrefix() {
        return "k3";
    }
}
