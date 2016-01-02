package org.jolene.threek;

import lombok.Data;

/**
 * 系统配置,该实例应该只会在系统初始化时诞生
 * <p>
 * 每一个数值都由数据库中保存的{@link org.jolene.threek.entity.SystemValue}保存
 * </p>
 * <p>
 *     <strong>重要</strong>
 *     整个系统只会认可一个SystemConfig,所以如果要对系统配置修改必须要经过确认以后才可以设置到系统任何的SystemConfig实例中
 * </p>
 *
 * @author Jolene
 */
@Data
public class SystemConfig {
    public static final String TITLE = "org.jolene.3k.title";


    /**
     * 需要执行配置,在该值为true时 除了配置以外其他所有操作都无法进行
     */
    private boolean configRequired = true;
    /**
     * 网站标题
     */
    private String title = "3k My Love";
    /**
     * 网站地址 必须以/结尾 比如 http://www.3k.com
     */
    private String url;
    /**
     * 欢迎消息
     */
    private String welcomeMessage = "欢迎来到 3K";
    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private String[] welcomeFeatures = new String[]{
            "安全稳定",
            "人多利高"
    };
    /**
     * 单笔金额
     */
    private int stock = 5000;
    /**
     * 日利率
     */
    private double rate = 0.015;
    /**
     * 最大手数
     */
    private int maxLots = 5;
    /**
     * 排队日期
     */
    private int queueDays = 5;


}
