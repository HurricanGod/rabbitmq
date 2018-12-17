package cn.hurrican.mq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/17
 * @Modified 16:01
 */
@Service
public class AckProducer implements RabbitTemplate.ConfirmCallback {


    @Autowired
    @Qualifier("defaultRabbitTemplate")
    private AmqpTemplate rabbitTemplate;

    /**
     * Confirmation callback.
     *
     * @param correlationData correlation data for the callback.
     * @param ack             true for ack, false for nack
     * @param cause           An optional cause, for nack, when available, otherwise null.
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

    }
}
