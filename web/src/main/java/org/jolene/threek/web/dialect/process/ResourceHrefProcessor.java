package org.jolene.threek.web.dialect.process;

import org.jolene.threek.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.exceptions.TemplateProcessingException;

import java.io.IOException;

/**
 * @author Jolene
 */
@Component
public class ResourceHrefProcessor extends AbstractSingleAttributeOverrideProcessor {

    public static final String TARGET_ATTR_NAME = "resourceHref";

    @Autowired
    private ResourceService resourceService;

    public ResourceHrefProcessor() {
        super(TARGET_ATTR_NAME);
    }

    @Override
    protected String getTargetAttributeValue(Object value) {
        if (value == null)
            return "";
        try {
            return resourceService.getResource(value.toString()).getURI().toString();
        } catch (IOException e) {
            throw new TemplateProcessingException("Bad URI", e);
        }
    }

}
