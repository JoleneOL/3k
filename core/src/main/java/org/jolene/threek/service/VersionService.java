package org.jolene.threek.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jolene.threek.entity.SystemValue;
import org.jolene.threek.entity.support.ResourceType;
import org.jolene.threek.repository.SystemValueRepository;
import org.jolene.threek.service.support.VersionUpgrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 版本维护服务
 *
 * @author Jolene
 */
@Service
public class VersionService {

    private static final Log log = LogFactory.getLog(VersionService.class);

    @Autowired
    private SystemValueRepository systemValueRepository;

    /**
     * 尝试系统升级,在发现需要升级以后将调用升级者,可以通过JDBC操作数据表.
     * <p>
     * <p>
     * 需要注意的是,版本升级采用的是逐步升级策略,比如数据库标记版本为1.0 然后更新到3.0 中间还存在2.0(这也是为什么版本标记是用枚举保
     * 存的原因),那么会让升级者升级到2.0再到3.0
     * </p>
     * <p>
     * 如果没有发现数据库版本标记 那么就默认为已经是当前版本了.
     * </p>
     *
     * @param systemStringVersionKey 保存版本信息的key,必须确保唯一;如果当前没有相关信息,则认为已经是当前版本了.
     * @param clazz                  维护版本信息的枚举类
     * @param currentVersion         当前版本
     * @param upgrade                负责提供系统升级业务的升级者
     * @param <T>                    维护版本信息的枚举类
     */
    @Transactional
    public <T extends Enum> void systemUpgrade(String systemStringVersionKey, Class<T> clazz, T currentVersion, VersionUpgrade<T> upgrade) {
        log.debug("Subsystem should upgrade to " + currentVersion);
        SystemValue databaseVersion = systemValueRepository.findOne(systemStringVersionKey);
        try {
            if (databaseVersion == null) {
                databaseVersion = new SystemValue();
                databaseVersion.setId(systemStringVersionKey);
                databaseVersion.setValue(String.valueOf(currentVersion.ordinal()));
                databaseVersion.setResourceType(ResourceType.text);
                systemValueRepository.save(databaseVersion);
//                upgrade(systemStringVersionKey, clazz, null, currentVersion, upgrade);
            } else {
                T database = clazz.getEnumConstants()[Integer.parseInt(databaseVersion.getValue())];
                //比较下等级
                if (database != currentVersion) {
                    upgrade(systemStringVersionKey, clazz, database, currentVersion, upgrade);
                }
            }
        } catch (Exception ex) {
            throw new InternalError("Failed Upgrade Database", ex);
        }

    }


    private <T extends Enum> void upgrade(String systemStringVersionKey, Class<T> clazz, T origin, T target, VersionUpgrade<T> upgrader)
            throws Exception {
        log.debug("Subsystem prepare to upgrade to " + target);
        boolean started = false;
        for (T step : clazz.getEnumConstants()) {
            if (origin == null || origin == step) {
                started = true;
            }

            if (started) {
                log.debug("Subsystem upgrade step: to " + target);
                upgrader.upgradeToVersion(step);
                log.debug("Subsystem upgrade step done");
            }

            if (step == target)
                break;
        }

        SystemValue databaseVersion = systemValueRepository.findOne(systemStringVersionKey);
        if (databaseVersion == null) {
            throw new InternalError("!!!No Current Version!!!");
        }
        databaseVersion.setValue(String.valueOf(target.ordinal()));
        systemValueRepository.save(databaseVersion);

    }
}
