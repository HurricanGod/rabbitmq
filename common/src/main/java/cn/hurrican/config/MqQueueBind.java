package cn.hurrican.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Hurrican
 * @Date 2018/12/3
 * @Modified 10:23
 */
@Configuration
public class MqQueueBind {

    @Bean(name = "userEntityQueue")
    public Queue getUserEntityQueue(){
        return new Queue("UserEntityQueue", true, false, false);
    }


    @Bean(name = "userEntityDirectExchange")
    public DirectExchange getUserEntityDirectExchange(Queue userEntityQueue){
        DirectExchange userEntityExchange = new DirectExchange("UserEntityExchange", true, false);
        BindingBuilder.bind(userEntityQueue).to(userEntityExchange);
        return userEntityExchange;
    }


    @Bean(name = "logQueue")
    public Queue getLogQueue(){
        return new Queue("logQueue", true, false, false);
    }

    @Bean(name = "logFanoutExchange")
    public FanoutExchange getLogFanoutExchange(){
        return new FanoutExchange("logFanoutExchange", true, false);
    }


    @Bean("logBinding")
    public Binding getLogBinding(Queue logQueue, Exchange logFanoutExchange){
        return new Binding(logQueue.getName(), Binding.DestinationType.QUEUE,
                logFanoutExchange.getName(), "logBindingKey", null);

    }

    @Bean(name = "appConfigQueue")
    public Queue getAppConfigQueue(){
        return new Queue("appConfigQueue", true, false, false);
    }


    @Bean(name = "appConfigFanoutExchange")
    public FanoutExchange getAppConfigFanoutExchange(){
        return new FanoutExchange("appConfigFanoutExchange", true, false);
    }

    @Bean(name = "appConfigBinding")
    public Binding getAppConfigBinding(Queue appConfigQueue, FanoutExchange appConfigFanoutExchange){
        return new Binding(appConfigQueue.getName(), Binding.DestinationType.QUEUE,
                appConfigFanoutExchange.getName(), "appConfigBindingKey", null);
    }


}
