package org.jolene.threek.service.impl;

import org.jolene.threek.entity.SystemValue;
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

    void asText(Consumer<String> consumer, SystemValue systemValue) {
        if (systemValue == null)
            return;
        // TODO 如果是资源的话 应该从资源中获取
        consumer.accept(systemValue.getValue());
    }

    void asTexts(Consumer<String[]> consumer, SystemValue systemValue) {
        if (systemValue == null)
            return;
        asText(text -> {
            if (text != null) {
                consumer.accept(text.split("\\|"));
            }
        }, systemValue);
    }

    void asBoolean(Consumer<Boolean> consumer, SystemValue systemValue) {
        if (systemValue == null)
            return;
        // TODO 如果是资源的话 应该从资源中获取
        consumer.accept(Boolean.parseBoolean(systemValue.getValue()));
    }

    void asInt(Consumer<Integer> consumer, SystemValue systemValue) {
        if (systemValue == null)
            return;
        // TODO 如果是资源的话 应该从资源中获取
        consumer.accept(Integer.parseInt(systemValue.getValue()));
    }

    void asDouble(Consumer<Double> consumer, SystemValue systemValue) {
        if (systemValue == null)
            return;
        // TODO 如果是资源的话 应该从资源中获取
        consumer.accept(Double.parseDouble(systemValue.getValue()));
    }

    @Override
    @Transactional(readOnly = true)
    public void asText(Consumer<String> consumer, String id) {
        asText(consumer, systemValueRepository.findOne(id));
    }

    @Override
    @Transactional(readOnly = true)
    public void asTexts(Consumer<String[]> consumer, String id) {
        asTexts(consumer, systemValueRepository.findOne(id));
    }

    @Override
    @Transactional(readOnly = true)
    public void asBoolean(Consumer<Boolean> consumer, String id) {
        asBoolean(consumer, systemValueRepository.findOne(id));
    }

    @Override
    @Transactional(readOnly = true)
    public void asInt(Consumer<Integer> consumer, String id) {
        asInt(consumer, systemValueRepository.findOne(id));
    }

    @Override
    @Transactional(readOnly = true)
    public void asDouble(Consumer<Double> consumer, String id) {
        asDouble(consumer, systemValueRepository.findOne(id));
    }
}
