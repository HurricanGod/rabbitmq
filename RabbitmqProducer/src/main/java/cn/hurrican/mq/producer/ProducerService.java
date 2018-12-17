package cn.hurrican.mq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/3
 * @Modified 10:05
 */
@Service
public class ProducerService {


    @Autowired
    @Qualifier("defaultRabbitTemplate")
    private AmqpTemplate defaultRabbitTemplate;


    public void sendMessageToFanoutExchange(String exchange, Object message, String routingKey){
        defaultRabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

}
