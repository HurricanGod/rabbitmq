package cn.hurrican.consumer.rabbitmq;

import cn.hurrican.consumer.model.UserEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/6
 * @Modified 12:16
 */
@Component
public class LogListener {

    @RabbitListener(queues = "logQueue")
    public void receiveAmqpMessage(UserEntity userEntity){
        System.out.println("userEntity = " + userEntity);
        System.out.println("Hello, LogQueue...");
    }
}
