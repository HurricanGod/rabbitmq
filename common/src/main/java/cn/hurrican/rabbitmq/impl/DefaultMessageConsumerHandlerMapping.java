package cn.hurrican.rabbitmq.impl;

import cn.hurrican.rabbitmq.MessageConsumerHandlerMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Component
public class DefaultMessageConsumerHandlerMapping implements MessageConsumerHandlerMapping {

    private HashMap<String, String> messageConsumerHandlerRegistry = new HashMap<>(32);

    @Override
    public HashMap<String, String> getMessageConsumerHandlerNameMap() {
        return messageConsumerHandlerRegistry;
    }

    @Override
    public String getMessageConsumerHandlerName(String transferEntityName) {
        return messageConsumerHandlerRegistry.get(transferEntityName);
    }

    @Override
    public void registerMessageConsumerHandler(String transferEntityName, String messageConsumerHandlerName) {
        messageConsumerHandlerRegistry.put(transferEntityName, messageConsumerHandlerName);
    }
}
