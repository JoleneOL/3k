package org.jolene.threek.web.dialect.process;

import org.springframework.stereotype.Component;

/**
 * @author Jolene
 */
@Component
public class ActiveParentProcessor extends ClassesByActiveMenuProcessor {
    public static final String ATTR_NAME = "activeparent";

    public ActiveParentProcessor() {
        super(ATTR_NAME);
    }

    @Override
    protected String hitClasses() {
        return "nav-active active";
    }

    @Override
    protected String noHitClasses() {
        return "";
    }
}
