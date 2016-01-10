package org.jolene.threek.web.dialect.process;

import org.jolene.threek.entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * 到{@link org.jolene.threek.entity.Email 邮件}详情的链接
 *
 * @author Jolene
 */
@Component
public class EmailHrefProcessor extends AbstractSingleAttributeOverrideProcessor {

    @Autowired
    private WebApplicationContext applicationContext;

    public EmailHrefProcessor() {
        super("emailHref");
    }

    /**
     * 从连接中抓去邮件id
     *
     * @param href 连接
     * @return 邮件id
     */
    public static Long emailIdFromHref(String href) {
        return Long.parseLong(href.substring(href.lastIndexOf('/') + 1));
    }

    @Override
    protected String getTargetAttributeValue(Object value) {
        if (value == null)
            return "";
        if (value instanceof Email) {
            return "/" + applicationContext.getServletContext().getContextPath() + "emails/" + ((Email) value).getId();
        }
        return null;
    }
}
