package org.jolene.threek;

import lombok.Data;

/**
 * 系统配置,该实例应该只会在系统初始化时诞生
 * <p>
 *     每一个数值都由数据库中保存的{@link org.jolene.threek.entity.SystemValue}保存
 * </p>
 * @author Jolene
 */
@Data
public class SystemConfig {
    public static final String TITLE = "org.jolene.3k.title";


    /**
     * 网站标题
     */
    private String title = "3k My Love";
    /**
     * 网站地址 必须以/结尾 比如 http://www.3k.com
     */
    private String url;
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
