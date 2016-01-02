package org.jolene.threek.web.dialect.process;

import org.springframework.stereotype.Component;

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
    protected String hitClasses() {
        return "active";
    }

    @Override
    protected String noHitClasses() {
        return "";
    }
}
