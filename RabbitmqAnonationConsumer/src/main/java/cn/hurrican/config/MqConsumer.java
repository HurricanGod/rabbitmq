package cn.hurrican.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableRabbit
@ImportResource("classpath:applicationContext.xml")
public class MqConsumer {




    @Bean(name = "rabbitListenerContainerFactory")
    public RabbitListenerContainerFactory getRabbitListenerContainerFactory(ConnectionFactory cachingConnectionFactory4Consumer){
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(cachingConnectionFactory4Consumer);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        containerFactory.setConcurrentConsumers(2);
        return containerFactory;
    }

}
