package cn.hurrican.rabbitmq;

import java.util.HashMap;

public interface MessageConsumerHandlerMapping {

    String ACTION = "action";

    String MESSAGE_CONSUMER_HANDLER_NAME = "handlerName";

    HashMap<String, String> getMessageConsumerHandlerNameMap();


    String getMessageConsumerHandlerName(String transferEntityName);

    void registerMessageConsumerHandler(String transferEntityName, String messageConsumerHandlerName);
}
