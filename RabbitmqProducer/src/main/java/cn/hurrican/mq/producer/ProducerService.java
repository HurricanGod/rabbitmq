package cn.hurrican.mq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/3
 * @Modified 10:05
 */
@Service
public class ProducerService {

    @Autowired
    private AmqpTemplate amqpTemplate;


    /**
     *
     * @param queueName 交换机名称
     * @param obj 消息对象
     */
    public void sendDataToDirectExchange(String queueName, Object obj) {
        amqpTemplate.convertAndSend(queueName, obj);
    }


    public void sendMessageToFanoutExchange(String exchange, Object message, String routingKey){
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }

}
