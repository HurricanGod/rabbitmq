package cn.hurrican.consumer.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import cn.hurrican.service.JacksonMessageConverter;

@Configuration
@EnableRabbit
@PropertySource(value = "classpath:rabbitmq.properties")
public class MqConsumer {


    @Bean(name = "basicListenerContainer")
    public SimpleMessageListenerContainer getBasicMessageListenerContainer(ConnectionFactory cachingConnectionFactory4Consumer,
                                                                           JacksonMessageConverter jsonMessageConverter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setMessageConverter(jsonMessageConverter);
        container.setConnectionFactory(cachingConnectionFactory4Consumer);
        container.setMaxConcurrentConsumers(10);
        container.setConcurrentConsumers(5);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }


    @Bean(name = "rabbitListenerContainerFactory")
    public RabbitListenerContainerFactory getRabbitListenerContainerFactory(ConnectionFactory cachingConnectionFactory4Consumer){
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(cachingConnectionFactory4Consumer);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        containerFactory.setConcurrentConsumers(2);
        return containerFactory;
    }

}
