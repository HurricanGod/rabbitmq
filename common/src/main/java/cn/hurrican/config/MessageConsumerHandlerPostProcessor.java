package cn.hurrican.config;

import cn.hurrican.rabbitmq.MessageConsumerHandler;
import cn.hurrican.rabbitmq.MessageConsumerHandlerMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/23
 * @Modified 11:00
 */
@Component
@Slf4j
public class MessageConsumerHandlerPostProcessor implements ApplicationContextAware {

    private ApplicationContext context;

    @PostConstruct
    public void init(){
        try{
            MessageConsumerHandlerMapping consumerHandlerHolder = context.getBean(MessageConsumerHandlerMapping.class);
            if(consumerHandlerHolder != null){
                Map<String, MessageConsumerHandler> consumerHandlerMap = context.getBeansOfType(MessageConsumerHandler.class);
                consumerHandlerMap.forEach((handlerName, handler) -> consumerHandlerHolder.registerMessageConsumerHandler(handler.supportEntityClassName(), handlerName));
            }
        }catch( Exception e){
            log.error("初始化消费者消息处理器时发生异常：{}", e);
        }
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
