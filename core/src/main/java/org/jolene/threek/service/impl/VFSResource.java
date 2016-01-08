package org.jolene.threek.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

/**
 * @author Jolene
 */
public class VFSResource extends AbstractResource implements Resource, WritableResource {

    private static final Log log = LogFactory.getLog(VFSResource.class);

    private final FileObject fileObject;
    private final URI uri;

    public VFSResource(FileObject fileObject, URI uri) {
        this.fileObject = fileObject;
        this.uri = uri;
    }

    @Override
    public boolean exists() {
        try {
            return fileObject.exists();
        } catch (FileSystemException e) {
            log.error("exists", e);
            return false;
        }
    }

    @Override
    public long contentLength() throws IOException {
        return fileObject.getContent().getSize();
    }

    @Override
    public long lastModified() throws IOException {
        return fileObject.getContent().getLastModifiedTime();
    }

    @Override
    public String getFilename() {
        return super.getFilename();
    }

    @Override
    public URI getURI() throws IOException {
        return uri;
    }

    @Override
    public URL getURL() throws IOException {
        return uri.toURL();
    }

    @Override
    public boolean isWritable() {
        try {
            return fileObject.isWriteable();
        } catch (FileSystemException e) {
            log.error("isWritable", e);
            return false;
        }
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return fileObject.getContent().getOutputStream();
    }

    @Override
    public String getDescription() {
        return fileObject.toString();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return fileObject.getContent().getInputStream();
    }


}
