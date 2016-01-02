package org.jolene.threek.web.dialect.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor;

import java.util.Arrays;

/**
 * 根据输入的参数和activeMenu对比 如果在范围内则加上相应的标签
 *
 * @author Jolene
 */
public abstract class ClassesByActiveMenuProcessor extends AbstractStandardSingleAttributeModifierAttrProcessor {

    private static final Log log = LogFactory.getLog(ClassesByActiveMenuProcessor.class);

    public static final int ATTR_PRECEDENCE = 21100;

    public static final String TARGET_ATTR_NAME = "class";

    public ClassesByActiveMenuProcessor(String name) {
        super(name);
    }

    /**
     * @return 命中的classes 比如nav-active active
     */
    protected abstract String hitClasses();

    /**
     * @return 没有命中的classes 比如nav-active active
     */
    protected abstract String noHitClasses();

    @Override
    protected String getTargetAttributeValue(Arguments arguments, Element element, String attributeName) {
        String activeMenu = (String) arguments.getLocalVariable("activeMenu");
        assert activeMenu != null;

        String[] value = super.getTargetAttributeValue(arguments, element, attributeName).split(",");
        if (Arrays.binarySearch(value, activeMenu) != -1)
            return hitClasses();
        return noHitClasses();
    }

    @Override
    public int getPrecedence() {
        return ATTR_PRECEDENCE;
    }


    @Override
    protected String getTargetAttributeName(
            final Arguments arguments, final Element element, final String attributeName) {
        return TARGET_ATTR_NAME;
    }


    @Override
    protected ModificationType getModificationType(
            final Arguments arguments, final Element element, final String attributeName, final String newAttributeName) {
        return ModificationType.APPEND_WITH_SPACE;
    }


    @Override
    protected boolean removeAttributeIfEmpty(
            final Arguments arguments, final Element element, final String attributeName, final String newAttributeName) {
        return true;
    }
}
