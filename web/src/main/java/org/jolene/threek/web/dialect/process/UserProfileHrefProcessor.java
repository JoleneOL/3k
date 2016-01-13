package org.jolene.threek.web.dialect.process;

import org.jolene.threek.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * 到{@link org.jolene.threek.entity.User 用户}详情的链接
 *
 * @author Jolene
 */
@Component
public class UserProfileHrefProcessor extends AbstractSingleAttributeOverrideProcessor {

    @Autowired
    private WebApplicationContext applicationContext;

    public UserProfileHrefProcessor() {
        super("userProfileHref");
    }


    @Override
    protected String getTargetAttributeValue(Object value) {
        if (value == null)
            return "";
        if (value instanceof User) {
            return "/" + applicationContext.getServletContext().getContextPath() + "profile/" + ((User) value).getId();
        }
        return null;
    }
}
