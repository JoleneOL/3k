package org.jolene.threek.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.jolene.threek.service.ResourceService;
import org.jolene.threek.service.VFSHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 生产环境静态资源处理器
 * <p>请务必设置系统属性</p>
 * <code>resource.resourcesUri</code>
 * <p>比如<code>http://res.baidu.cn/</code></p>
 * <code>resource.resourcesHome</code>
 * <p>比如<code>file://D:/resources</code></p>
 *
 * @author Jolene
 */
@Service("resourceService")
@Profile("container")
public class VFSResourceService implements ResourceService {

    private static final Log log = LogFactory.getLog(VFSResourceService.class);

    protected URI uriPrefix;
    protected URI fileHome;
    @Autowired
    private VFSHelper vfsHelper;

    public VFSResourceService(String uri, String home, String badMessage) {
        if (uri == null) {
            throw new IllegalStateException(badMessage);
        }
        if (home == null) {
            throw new IllegalStateException(badMessage);
        }
        try {
            log.info("" + this + " Use ResourceURI:" + uri);
            this.uriPrefix = new URI(uri);
            this.fileHome = new URI(home);
        } catch (URISyntaxException e) {
            log.error("解析失败", e);
            throw new InternalError("解析失败");
        }
    }

    @Autowired
    public VFSResourceService(Environment environment) {
        this(environment.getProperty("resource.resourcesUri", (String) null)
                , environment.getProperty("resource.resourcesHome", (String) null)
                , "请设置resource.resourcesUri和resource.resourcesHome属性");
    }

    @Override
    public Resource uploadResource(String path, InputStream data) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(fileHome.toString());
        if (!stringBuilder.toString().endsWith("/") && !path.startsWith("/"))
            stringBuilder.append("/");
        stringBuilder.append(path);

        vfsHelper.handle(stringBuilder.toString(), file -> {
            OutputStream out = file.getContent().getOutputStream();
            try {
                StreamUtils.copy(data, out);
            } catch (IOException e) {
                throw new FileSystemException(e);
            } finally {
                try {
                    data.close();
                    out.close();
                } catch (IOException e) {
                    log.info("Exception on close stream." + e);
                }
            }
        });
        return getResource(path);
    }

    @Override
    public Resource getResource(String path) {
        StringBuilder stringBuilder = new StringBuilder(uriPrefix.toString());
        if (!stringBuilder.toString().endsWith("/") && !path.startsWith("/"))
            stringBuilder.append("/");
        stringBuilder.append(path);


        StringBuilder fileBuilder = new StringBuilder(fileHome.toString());
        if (!fileBuilder.toString().endsWith("/") && !path.startsWith("/"))
            fileBuilder.append("/");
        fileBuilder.append(path);

        try {
            FileObject fileObject = vfsHelper.handle(fileBuilder.toString(), null);
            return new VFSResource(fileObject, new URI(stringBuilder.toString()));
        } catch (FileSystemException e) {
            log.error("解释资源时", e);
            return null;
        } catch (URISyntaxException e) {
            log.error("解释资源时", e);
            return null;
        }
    }

    @Override
    public void deleteResource(String path) throws IOException {
        if (path == null)
            return;
        StringBuilder stringBuilder = new StringBuilder(fileHome.toString());
        if (!stringBuilder.toString().endsWith("/") && !path.startsWith("/"))
            stringBuilder.append("/");
        stringBuilder.append(path);

        vfsHelper.handle(stringBuilder.toString(), FileObject::delete);
    }
}
