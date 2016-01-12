package org.jolene.threek.entity.support;

/**
 * 匹配状态
 *
 * @author Jolene
 */
public enum MatchingStatus {
    /**
     * 已匹配,初始状态
     */
    matched,
    /**
     * 凭证已经上传
     */
    uploaded,
    /**
     * 已确认
     */
    confirmed
}
