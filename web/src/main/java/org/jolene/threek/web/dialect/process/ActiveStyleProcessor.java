package org.jolene.threek.web.dialect.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor;

/**
 * @author Jolene
 */
@Component
public class ActiveStyleProcessor extends AbstractStandardSingleAttributeModifierAttrProcessor {

    public static final int ATTR_PRECEDENCE = 21100;
    public static final String TARGET_ATTR_NAME = "style";
    private static final Log log = LogFactory.getLog(ActiveStyleProcessor.class);

    protected ActiveStyleProcessor() {
        super("activestyle");
    }

    @Override
    protected String getTargetAttributeValue(Arguments arguments, Element element, String attributeName) {
        NestableNode current = element;
        int count = 0;
        while (count < 3) {
            count++;
            if (!current.hasParent())
                break;
            current = current.getParent();
            if (current.hasNodeProperty(ActiveParentProcessor.ACTIVE_PARENT_PROPERTY)) {
                return "display: block;";
            }
        }
        return "";
    }

    @Override
    protected String getTargetAttributeName(Arguments arguments, Element element, String attributeName) {
        return TARGET_ATTR_NAME;
    }

    @Override
    protected ModificationType getModificationType(Arguments arguments, Element element, String attributeName, String newAttributeName) {
        return ModificationType.APPEND_WITH_SPACE;
    }

    @Override
    protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String attributeName, String newAttributeName) {
        return true;
    }

    @Override
    public int getPrecedence() {
        return ATTR_PRECEDENCE;
    }
}
