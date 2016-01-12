package org.jolene.threek.web;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 静态辅助
 *
 * @author Jolene
 */
public class MVCUtils {

    /**
     * 在页面展示一条成功的弹窗
     * <p>
     * 它的实现前提是页面载入了fragments/common/alertFunctions
     * </p>
     *
     * @param attributes mvc变量
     * @param message    信息
     */
    public static void addSuccessMessage(RedirectAttributes attributes, String message) {
        attributes.addFlashAttribute("successMessage", message);
    }

    /**
     * 在页面展示一条成功的弹窗
     * <p>
     * 它的实现前提是页面载入了fragments/common/alertFunctions
     * </p>
     *
     * @param attributes mvc变量
     * @param message    信息
     */
    public static void addInfoMessage(RedirectAttributes attributes, String message) {
        attributes.addFlashAttribute("infoMessage", message);
    }

    /**
     * 在页面展示一条成功的弹窗
     * <p>
     * 它的实现前提是页面载入了fragments/common/alertFunctions
     * </p>
     *
     * @param attributes mvc变量
     * @param message    信息
     */
    public static void addWarningMessage(RedirectAttributes attributes, String message) {
        attributes.addFlashAttribute("warningMessage", message);
    }

    /**
     * 在页面展示一条成功的弹窗
     * <p>
     * 它的实现前提是页面载入了fragments/common/alertFunctions
     * </p>
     *
     * @param attributes mvc变量
     * @param message    信息
     */
    public static void addDangerMessage(RedirectAttributes attributes, String message) {
        attributes.addFlashAttribute("dangerMessage", message);
    }

}
