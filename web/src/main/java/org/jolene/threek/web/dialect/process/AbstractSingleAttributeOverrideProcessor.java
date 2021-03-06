package org.jolene.threek.web.dialect.process;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.standard.expression.Assignation;
import org.thymeleaf.standard.expression.AssignationSequence;
import org.thymeleaf.standard.expression.AssignationUtils;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor;

/**
 * 单个键值对产生一个html属性值并且覆盖原属性
 *
 * @author Jolene
 */
public abstract class AbstractSingleAttributeOverrideProcessor extends AbstractStandardSingleAttributeModifierAttrProcessor {


    public static final int ATTR_PRECEDENCE = 21100;

    public AbstractSingleAttributeOverrideProcessor(String attributeName) {
        super(attributeName);
    }

    @Override
    protected String getTargetAttributeValue(Arguments arguments, Element element, String attributeName) {
        final Configuration configuration = arguments.getConfiguration();
        Assignation assignation = getAssignation(arguments, element, attributeName);

        final IStandardExpression rightExpr = assignation.getRight();
        final Object rightValue = rightExpr.execute(configuration, arguments);

        return getTargetAttributeValue(rightValue);
    }

    protected abstract String getTargetAttributeValue(Object value);

    private Assignation getAssignation(Arguments arguments, Element element, String attributeName) {
        final String attributeValue = element.getAttributeValue(attributeName);

        final Configuration configuration = arguments.getConfiguration();

        final AssignationSequence assignations =
                AssignationUtils.parseAssignationSequence(
                        configuration, arguments, attributeValue, false /* no parameters without value */);
        if (assignations == null) {
            throw new TemplateProcessingException(
                    "Could not parse value as attribute assignations: \"" + attributeValue + "\"");
        }
        if (assignations.size() != 1)
            throw new TemplateProcessingException("1 attribute assignation is excepted, but \"" + attributeValue + "\"");


        return assignations.getAssignations().get(0);
    }

    @Override
    protected String getTargetAttributeName(Arguments arguments, Element element, String attributeName) {
        final Configuration configuration = arguments.getConfiguration();
        Assignation assignation = getAssignation(arguments, element, attributeName);
        final IStandardExpression leftExpr = assignation.getLeft();
        final Object leftValue = leftExpr.execute(configuration, arguments);
        return (leftValue == null ? null : leftValue.toString());
    }

    @Override
    protected ModificationType getModificationType(Arguments arguments, Element element, String attributeName, String newAttributeName) {
        return ModificationType.SUBSTITUTION;
    }

    @Override
    protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String attributeName, String newAttributeName) {
        return false;
    }

    @Override
    public int getPrecedence() {
        return ATTR_PRECEDENCE;
    }
}
