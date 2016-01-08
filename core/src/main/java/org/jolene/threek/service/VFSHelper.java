package org.jolene.threek.service;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.jolene.threek.service.impl.FileObjectConsumer;
import org.springframework.stereotype.Service;

/**
 * @author Jolene
 */
@Service
public class VFSHelper {

    private final FileSystemOptions options = new FileSystemOptions();
    private boolean passive;

    public VFSHelper() {
        super();
        SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(options, false);
        SftpFileSystemConfigBuilder.getInstance().setTimeout(options, 30000);

        FtpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(options, false);
        FtpFileSystemConfigBuilder.getInstance().setDataTimeout(options, 30000);
        FtpFileSystemConfigBuilder.getInstance().setSoTimeout(options, 30000);
        FtpFileSystemConfigBuilder.getInstance().setPassiveMode(options, passive);
    }

    public FileObject handle(String name, FileObjectConsumer consumer) throws FileSystemException {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        manager.init();
        try {
            FileObject file = resolveFile(name, manager);
            try {
                if (consumer != null)
                    consumer.accept(file);
                return file;
            } catch (FileSystemException ex) {
                togglePassive();
                try {
                    //noinspection ConstantConditions
                    if (consumer != null)
                        consumer.accept(file);
                    return file;
                } catch (FileSystemException e) {
                    throw e;
                }
            }
        } finally {
            manager.close();
        }
    }

    public FileObject resolveFile(String name, FileSystemManager manager) throws FileSystemException {
        return manager.resolveFile(name, options);
    }

    public void togglePassive() {
        passive = !passive;
        FtpFileSystemConfigBuilder.getInstance().setPassiveMode(options, passive);
    }
}
