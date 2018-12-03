package cn.hurrican.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Hurrican
 * @Description:
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
        BindingBuilder.bind(userEntityQueue).to(userEntityExchange).with("");
        return userEntityExchange;
    }


}
