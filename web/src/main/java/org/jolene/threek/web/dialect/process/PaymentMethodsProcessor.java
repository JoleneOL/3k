package org.jolene.threek.web.dialect.process;

import org.jolene.threek.common.PaymentMethod;
import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractIterationAttrProcessor;

/**
 * @author Jolene
 */
@Component
public class PaymentMethodsProcessor extends AbstractIterationAttrProcessor {
    public static final int ATTR_PRECEDENCE = 21100;
    public static final String ATTR_NAME = "paymentMethods";

    protected PaymentMethodsProcessor() {
        super(ATTR_NAME);
    }

    @Override
    public int getPrecedence() {
        return ATTR_PRECEDENCE;
    }

    @Override
    protected IterationSpec getIterationSpec(Arguments arguments, Element element, String attributeName) {
        final String attributeValue = element.getAttributeValue(attributeName);
        // 如果有空格 那么就什么什么滴
        return new IterationSpec(attributeValue, null, PaymentMethod.values());
    }

    @Override
    protected void processClonedHostIterationElement(Arguments arguments, Element iteratedChild, String attributeName) {

    }
}
