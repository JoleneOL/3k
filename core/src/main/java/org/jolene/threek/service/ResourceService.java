package org.jolene.threek.service;

import org.jolene.threek.service.impl.VFSResourceService;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * 伙伴plus共享的静态资源服务
 * <p>在不同的环境中将有不同的实现管理该服务</p>
 * <p>在生产环境中使用的将是{@link VFSResourceService}</p>
 *
 * @author Jolene
 */
public interface ResourceService {

    /**
     * 上传资源
     *
     * @param path 资源路径（相对）
     * @param data 数据
     * @return 新资源的资源定位符
     * @throws IOException 保存时出错
     */
    Resource uploadResource(String path, InputStream data) throws IOException;

    /**
     * 获取指定资源的资源定位符
     *
     * @param path 资源路径（相对）
     * @return 资源实体
     */
    Resource getResource(String path);

    /**
     * 删除资源
     *
     * @param path 资源路径（相对）
     * @throws IOException 删除时错误
     */
    void deleteResource(String path) throws IOException;
}
