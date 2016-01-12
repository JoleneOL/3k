package org.jolene.threek;

import lombok.Data;
import org.jolene.threek.support.InterestReward;

import java.util.HashMap;
import java.util.Map;

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
    public static final String TITLE = "org.jolene.3k.title";
    public static final String URL = "org.jolene.3k.url";
    public static final String WELCOME_MESSAGE = "org.jolene.3k.welcomeMessage";
    public static final String WELCOME_FEATURES = "org.jolene.3k.welcomeFeatures";
    public static final String REGISTER_WELCOME_MESSAGE = "org.jolene.3k.regWelcomeMessage";
    public static final String ONLY_INVITE = "org.jolene.3k.onlyInvite";
    public static final String STOCK = "org.jolene.3k.stock";
    public static final String RATE = "org.jolene.3k.rate";
    public static final String MAX_LOTS = "org.jolene.3k.maxLots";
    public static final String QUEUE_DAYS = "org.jolene.3k.queueDays";
    public static final String MAX_OPERATE_HOURS = "org.jolene.3k.maxOperateHours";
    public static final String MAX_ORDERS = "org.jolene.3k.maxOrders";
    public static final String INSPECT_START = "org.jolene.3k.inspectStartDayOfMonth";
    public static final String INSPECT_END = "org.jolene.3k.inspectEndDayOfMonth";
    // 每月1-15号是考核期，在此期间每个会员账户必须生成一次【提供互助】订单，如若在规定期间未生成，
    // 此账户自动冻结直到生成【提供互助】订单自动解封；在冻结期内不享受任何收益
    // 还需要一个冻结历史来计算利益
    public static final String DIRECT_REWARD_RATE = "org.jolene.3k.directRewardRate";
    public static final String DIRECT_REWARD_RATE2 = "org.jolene.3k.directRewardRate2";
    /**
     * 储存时按照 0.1,1.0[|0.1,1.0]*保存为文本
     */
    public static final String INTEREST_REWARDS = "org.jolene.3k.interestRewards";
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

    /**
     * 注册页欢迎信息
     */
    private String regWelcomeMessage = "欢迎注册";

    /**
     * 是否只允许邀请注册
     */
    private boolean onlyInvite = true;

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private String[] welcomeFeatures = new String[]{
            "安全稳定",
            "人多利高"
    };
    /**
     * 首页头部富文本
     */
    private String indexTopNotice = "&nbsp;";
    /**
     * 首页尾部富文本
     */
    private String indexBottomNotice = "&nbsp;";
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
     * 周期
     */
    private int queueDays = 10;
    /**
     * 最大操作小时
     */
    private int maxOperateHours = 24;
    /**
     * 最多未完成订单数
     */
    private int maxOrders = 5;
    /**
     * 考核期起始
     */
    private int inspectStartDayOfMonth = 1;
    /**
     * 考核期终止
     */
    private int inspectEndDayOfMonth = 15;
    /**
     * 直推（子代）奖励 跟投资金额挂钩
     * <p>可设置为0取消该奖励</p>
     */
    private double directRewardRate = 0.1;
    /**
     * 第二直推（二代）奖励 跟投资金额挂钩
     * <p>可设置为0取消该奖励</p>
     */
    private double directRewardRate2 = 0.05;
    private Map<Integer, InterestReward> interestRewards;
    /**
     * 区域经理奖励,单位时间结算一次
     */
    private double leaderRate = 0.006;
    /**
     * 单位时间直接推荐人数
     */
    private int leaderStopRecommends = 10;
    /**
     * 单位时间内会员规模增长量
     */
    private int leaderStopMembers = 80;
    /**
     * 单位时间内团队总投资额
     */
    private int leaderStopInvests = 800000;

    {
        interestRewards = new HashMap<>();
        interestRewards.put(1, new InterestReward(0.1, 1, 0));
        interestRewards.put(2, new InterestReward(0.09, 1, 0));
        interestRewards.put(3, new InterestReward(0.08, 2, 0));
        interestRewards.put(4, new InterestReward(0.07, 2, 0));
        interestRewards.put(5, new InterestReward(0.06, 3, 0));
        interestRewards.put(6, new InterestReward(0.05, 3, 0));
        interestRewards.put(7, new InterestReward(0.04, 4, 0));
        interestRewards.put(8, new InterestReward(0.03, 4, 0));
        interestRewards.put(9, new InterestReward(0.02, 5, 0));
        interestRewards.put(10, new InterestReward(0.01, 5, 0));
    }

    public String getHtmlTitle() {
        return "<span>[</span> " + title + " <span>]</span>";
    }

    public InterestReward getInterestReward1() {
        return interestRewards.get(1);
    }

    public void setInterestReward1(InterestReward reward) {
        interestRewards.put(1, reward);
    }

    public InterestReward getInterestReward2() {
        return interestRewards.get(2);
    }

    public void setInterestReward2(InterestReward reward) {
        interestRewards.put(2, reward);
    }

    public InterestReward getInterestReward3() {
        return interestRewards.get(3);
    }

    public void setInterestReward3(InterestReward reward) {
        interestRewards.put(3, reward);
    }

    public InterestReward getInterestReward4() {
        return interestRewards.get(4);
    }

    public void setInterestReward4(InterestReward reward) {
        interestRewards.put(4, reward);
    }

    public InterestReward getInterestReward5() {
        return interestRewards.get(5);
    }

    public void setInterestReward5(InterestReward reward) {
        interestRewards.put(5, reward);
    }

    public InterestReward getInterestReward6() {
        return interestRewards.get(6);
    }

    public void setInterestReward6(InterestReward reward) {
        interestRewards.put(6, reward);
    }

    public InterestReward getInterestReward7() {
        return interestRewards.get(7);
    }

    public void setInterestReward7(InterestReward reward) {
        interestRewards.put(7, reward);
    }

    public InterestReward getInterestReward8() {
        return interestRewards.get(8);
    }

    public void setInterestReward8(InterestReward reward) {
        interestRewards.put(8, reward);
    }

    public InterestReward getInterestReward9() {
        return interestRewards.get(9);
    }

    public void setInterestReward9(InterestReward reward) {
        interestRewards.put(9, reward);
    }

    public InterestReward getInterestReward10() {
        return interestRewards.get(10);
    }

    public void setInterestReward10(InterestReward reward) {
        interestRewards.put(10, reward);
    }

}
