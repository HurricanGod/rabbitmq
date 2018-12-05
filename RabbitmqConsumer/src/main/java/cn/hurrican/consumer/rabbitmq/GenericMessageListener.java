package cn.hurrican.consumer.rabbitmq;

import cn.hurrican.rabbitmq.MessageConsumerHandler;
import cn.hurrican.rabbitmq.MessageConsumerHandlerMapping;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GenericMessageListener implements ChannelAwareMessageListener, ApplicationContextAware {


    private ApplicationContext ctx;

    @Autowired
    @Qualifier("basicListenerContainer")
    private SimpleMessageListenerContainer defaultMessageListenerContainer;

    @Autowired
    private List<Queue> queueList;

    @PostConstruct
    public void init(){

        defaultMessageListenerContainer.setMessageListener(this);
        if(queueList != null && queueList.size() > 0){
            System.out.println("queueList = " + queueList);
            Queue[] queues = new Queue[queueList.size()];
            queueList.toArray(queues);
            defaultMessageListenerContainer.setQueues(queues);

        }
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();
        Object action = headers.get(MessageConsumerHandlerMapping.ACTION);
        Object handlerName = headers.get(MessageConsumerHandlerMapping.MESSAGE_CONSUMER_HANDLER_NAME);
        System.out.println("action = " + action);
        System.out.println("handlerName = " + handlerName);
        try{
            MessageConsumerHandler consumerHandler = ctx.getBean(handlerName + "", MessageConsumerHandler.class);
            consumerHandler.invoke(message, channel, (Integer) action);
            channel.basicAck(messageProperties.getDeliveryTag(), false);
        }catch(Exception e){
            e.printStackTrace();
            log.error("Rabbitmq消费者消费消息时出现异常：{}", e);
            channel.basicReject(messageProperties.getDeliveryTag(), true);
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
