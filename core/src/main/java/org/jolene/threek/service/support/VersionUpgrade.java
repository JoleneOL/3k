package org.jolene.threek.service.support;

/**
 * 版本升级要做的作业
 *
 * @author Jolene
 */
@FunctionalInterface
public interface VersionUpgrade<T> {


    /**
     * 从最近版本升级到step版本.
     *
     * @param version 要升级的版本
     */
    void upgradeToVersion(T version) throws Exception;
}
