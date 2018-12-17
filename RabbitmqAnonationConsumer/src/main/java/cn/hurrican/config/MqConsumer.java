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
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableRabbit
@ImportResource("classpath:applicationContext.xml")
public class MqConsumer {


    @Bean(name = "threadPoolExecutor")
    public ThreadPoolExecutor createExecutor(){
        CustomizableThreadFactory threadFactory = new CustomizableThreadFactory();
        threadFactory.setThreadNamePrefix("rabbitmq-message-consumer-");
        return new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
    }



    @Bean(name = "rabbitListenerContainerFactory")
    public RabbitListenerContainerFactory getRabbitListenerContainerFactory(ConnectionFactory cachingConnectionFactory4Consumer,
                                                                            ThreadPoolExecutor threadPoolExecutor){
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(cachingConnectionFactory4Consumer);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        containerFactory.setConcurrentConsumers(2);
        containerFactory.setMaxConcurrentConsumers(30);
        ExponentialBackOff recoveryBackOff = new ExponentialBackOff();
        recoveryBackOff.setInitialInterval(1000);
        recoveryBackOff.setMaxInterval(10000);
        recoveryBackOff.setMultiplier(2);
        containerFactory.setRecoveryBackOff(recoveryBackOff);
        containerFactory.setTaskExecutor(threadPoolExecutor);
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
