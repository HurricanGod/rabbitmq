package cn.hurrican.rabbitmq.impl;

import cn.hurrican.rabbitmq.MessageConsumerHandler;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
@Slf4j
public class GenericMessageListener implements ChannelAwareMessageListener, ApplicationContextAware {


    private ApplicationContext ctx;

    @Autowired
    @Qualifier("basicListenerContainer")
    private SimpleMessageListenerContainer defaultMessageListenerContainer;

    @PostConstruct
    public void init(){
        defaultMessageListenerContainer.setMessageListener(this);
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        Object action = headers.get("action");
        Object className = headers.get("className");
        System.out.println("action = " + action);
        System.out.println("className = " + className);
        try{
            MessageConsumerHandler consumerHandler = ctx.getBean(className + "", MessageConsumerHandler.class);
            System.out.println("consumerHandler != null ? " + (consumerHandler != null));
            consumerHandler.invoke(message, channel, (Integer) action);
        }catch(Exception e){
            e.printStackTrace();
            log.error("", e);
        }


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }


    public ApplicationContext getApplicationContext() {
        return ctx;
    }
}
