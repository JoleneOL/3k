package org.jolene.threek.service.impl;

import org.jolene.threek.repository.SystemValueRepository;
import org.jolene.threek.service.SystemValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

/**
 * @author Jolene
 */
@Service
public class SystemValueServiceImpl implements SystemValueService {

    @Autowired
    private SystemValueRepository systemValueRepository;

    @Override
    @Transactional(readOnly = true)
    public void asText(Consumer<String> consumer, String id) {

    }

    @Override
    @Transactional(readOnly = true)
    public void asInt(Consumer<Integer> consumer, String id) {

    }

    @Override
    @Transactional(readOnly = true)
    public void asDouble(Consumer<Double> consumer, String id) {

    }
}
