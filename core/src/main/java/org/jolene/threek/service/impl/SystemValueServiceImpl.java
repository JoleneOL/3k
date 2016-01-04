package org.jolene.threek.service.impl;

import org.jolene.threek.entity.SystemValue;
import org.jolene.threek.entity.support.ResourceType;
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
    public void asTexts(String[] texts, String id) {
        asText(String.join("|", texts), id);
    }

    @Override
    public void asText(String text, String id) {
        SystemValue systemValue = systemValueRepository.findOne(id);
        if (systemValue == null) {
            systemValue = new SystemValue();
            systemValue.setId(id);
        }
        asText(text, systemValue);
    }

    @Override
    public void asInt(int value, String id) {
        asText(String.valueOf(value), id);
    }

    @Override
    public void asDouble(double value, String id) {
        asText(String.valueOf(value), id);
    }

    @Override
    @Transactional
    public void asBoolean(boolean value, String id) {
        asText(String.valueOf(value), id);
    }

    private void asText(String text, SystemValue systemValue) {
        if (text == null)
            return;
        if (text.length() < 400)
            systemValue.setResourceType(ResourceType.text);
        else
            throw new IllegalArgumentException("too big resource.");
        systemValue.setValue(text);
        systemValueRepository.save(systemValue);
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
