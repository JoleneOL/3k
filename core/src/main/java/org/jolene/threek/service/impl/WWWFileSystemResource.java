package org.jolene.threek.service.impl;

import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Jolene
 */
public class WWWFileSystemResource extends FileSystemResource {

    private final String url;

    public WWWFileSystemResource(File file, String context, String path) {
        super(file);
        StringBuilder stringBuilder = new StringBuilder("http://localhost:8080");
        stringBuilder.append(context).append(LocalResourceService.ResourcePath);
        if (!path.startsWith("/"))
            stringBuilder.append("/");
        stringBuilder.append(path);
        this.url = stringBuilder.toString();
    }

    @Override
    public URI getURI() throws IOException {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    @Override
    public URL getURL() throws IOException {
        return new URL(url);
    }
}
