package cn.hurrican.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/6
 * @Modified 12:16
 */
@Component
public class LogListener {

    @RabbitListener(queues = "logQueue", containerFactory = "rabbitListenerContainerFactory")
    public void receiveAmqpMessage(Message msg, Channel channel) {
        System.out.println("element = " + msg);
        System.out.println("Hello, LogQueue...");
        try {
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
