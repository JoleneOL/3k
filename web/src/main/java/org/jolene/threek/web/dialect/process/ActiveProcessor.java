package org.jolene.threek.web.dialect.process;

import org.springframework.stereotype.Component;
import org.thymeleaf.dom.Element;

/**
 * @author Jolene
 */
@Component
public class ActiveProcessor extends ClassesByActiveMenuProcessor {
    public static final String ATTR_NAME = "active";

    public ActiveProcessor() {
        super(ATTR_NAME);
    }

    @Override
    protected String hitClasses(Element element) {
        return "active";
    }

    @Override
    protected String noHitClasses(Element element) {
        return "";
    }
}
