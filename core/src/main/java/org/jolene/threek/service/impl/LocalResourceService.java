package org.jolene.threek.service.impl;

import org.jolene.threek.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jolene
 */
@Service
@Profile("!container")
public class LocalResourceService implements ResourceService {

    public static final String ResourcePath = "/_resources";

    private final File home;
    private final String context;

    @Autowired
    public LocalResourceService(WebApplicationContext webApplicationContext) {
        this.home = new File(webApplicationContext.getServletContext().getRealPath(ResourcePath));
        //noinspection ResultOfMethodCallIgnored
        this.home.mkdirs();
        this.context = webApplicationContext.getServletContext().getContextPath();
    }

    @Override
    public Resource uploadResource(String path, InputStream data) throws IOException {
        File file = new File(home, path);
        //noinspection ResultOfMethodCallIgnored
        file.getParentFile().mkdirs();
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            StreamUtils.copy(data, outputStream);
        }
        return new WWWFileSystemResource(file, context, path);
    }

    @Override
    public Resource getResource(String path) {

        return new WWWFileSystemResource(new File(home, path), context, path);
    }

    @Override
    public void deleteResource(String path) throws IOException {
        File file = new File(home, path);
        if (file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
//                throw new IOException("删除失败 delete() is false "+file);
        }

    }
}
