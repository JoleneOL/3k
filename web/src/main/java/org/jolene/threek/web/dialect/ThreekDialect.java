package org.jolene.threek.web.dialect;

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
    @Override
    public Map<String, Object> getAdditionalExpressionObjects(IProcessingContext processingContext) {
        return new HashMap<>();
    }

    @Override
    public String getPrefix() {
        return "3k";
    }
}
