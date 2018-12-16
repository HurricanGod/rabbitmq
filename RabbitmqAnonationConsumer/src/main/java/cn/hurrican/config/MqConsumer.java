package cn.hurrican.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.HashMap;

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
        containerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        return containerFactory;
    }

    @Bean(name = "debugQueue")
    public Queue getDebugQueue() {
        return new Queue("debugQueue", true, false, false);
    }

    @Bean(name = "debugFanoutExchange")
    public FanoutExchange getDebugFanoutExchange() {
        return new FanoutExchange("debugFanoutExchange", true, false);
    }


    @Bean(name = "debugBinding")
    public Binding getDebugBinding(FanoutExchange debugFanoutExchange, Queue debugQueue) {
        return new Binding(debugQueue.getName(), Binding.DestinationType.QUEUE,
                debugFanoutExchange.getName(), "debugRoutingKey", new HashMap<>(2));
    }



}
