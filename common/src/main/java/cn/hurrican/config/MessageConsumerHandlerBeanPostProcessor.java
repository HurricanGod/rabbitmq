package cn.hurrican.config;

import cn.hurrican.rabbitmq.MessageConsumerHandler;
import cn.hurrican.rabbitmq.MessageConsumerHandlerMapping;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MessageConsumerHandlerBeanPostProcessor implements BeanPostProcessor,ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try{
            if(bean instanceof MessageConsumerHandler){
                MessageConsumerHandlerMapping consumerHandler = context.getBean(MessageConsumerHandlerMapping.class);
                if(consumerHandler != null){
                    String className = ((MessageConsumerHandler) bean).supportEntityClassName();
                    System.out.println("className = " + className);
                    Optional.ofNullable(className).ifPresent(e -> consumerHandler.registerMessageConsumerHandler(e, beanName));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
