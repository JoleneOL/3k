package org.jolene.threek.service.impl;

import org.jolene.threek.SystemConfig;
import org.jolene.threek.repository.SystemValueRepository;
import org.jolene.threek.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jolene
 */
@Service
public class AppServiceImpl implements AppService {

    private SystemConfig systemConfig;
    @Autowired
    private SystemValueRepository systemValueRepository;

    @Override
    public synchronized SystemConfig currentSystemConfig() {
        if (systemConfig==null){
            systemConfig = new SystemConfig();

        }
        return null;
    }
}
