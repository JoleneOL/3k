package org.jolene.threek.web.dialect.process;

import org.springframework.stereotype.Component;
import org.thymeleaf.dom.Element;

/**
 * @author Jolene
 */
@Component
public class ActiveParentProcessor extends ClassesByActiveMenuProcessor {
    public static final String ATTR_NAME = "activeparent";
    public static final String ACTIVE_PARENT_PROPERTY = "activeParent";

    public ActiveParentProcessor() {
        super(ATTR_NAME);
    }

    @Override
    protected String hitClasses(Element element) {
        element.setNodeProperty(ACTIVE_PARENT_PROPERTY,true);
        return "nav-active active";
    }

    @Override
    protected String noHitClasses(Element element) {
        return "";
    }
}
