package org.jolene.threek;

import lombok.Data;

/**
 * 系统配置,该实例应该只会在系统初始化时诞生
 * <p>
 * 每一个数值都由数据库中保存的{@link org.jolene.threek.entity.SystemValue}保存
 * </p>
 * <p>
 * <strong>重要</strong>
 * 整个系统只会认可一个SystemConfig,所以如果要对系统配置修改必须要经过确认以后才可以设置到系统任何的SystemConfig实例中
 * </p>
 *
 * @author Jolene
 */
@Data
public class SystemConfig {


    public static final String CONFIG_REQUIRED = "org.jolene.3k.configRequired";
    /**
     * 需要执行配置,在该值为true时 除了配置以外其他所有操作都无法进行
     */
    private boolean configRequired = true;
    public static final String TITLE = "org.jolene.3k.title";
    /**
     * 网站标题
     */
    private String title = "3k My Love";

    public String getHtmlTitle() {
        return "<span>[</span> " + title + " <span>]</span>";
    }

    public static final String URL = "org.jolene.3k.url";
    /**
     * 网站地址 必须以/结尾 比如 http://www.3k.com
     */
    private String url;
    public static final String WELCOME_MESSAGE = "org.jolene.3k.welcomeMessage";
    /**
     * 欢迎消息
     */
    private String welcomeMessage = "欢迎来到 3K";
    public static final String WELCOME_FEATURES = "org.jolene.3k.welcomeFeatures";
    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private String[] welcomeFeatures = new String[]{
            "安全稳定",
            "人多利高"
    };
    public static final String STOCK = "org.jolene.3k.stock";
    /**
     * 单笔金额
     */
    private int stock = 5000;
    public static final String RATE = "org.jolene.3k.rate";
    /**
     * 日利率
     */
    private double rate = 0.015;
    public static final String MAX_LOTS = "org.jolene.3k.maxLots";
    /**
     * 最大手数
     */
    private int maxLots = 5;
    public static final String QUEUE_DAYS = "org.jolene.3k.queueDays";
    /**
     * 排队日期
     */
    private int queueDays = 5;


}
