package cn.hurrican.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/11/30
 * @Modified 17:23
 */
public interface MessageConsumerHandler {


    Object invoke(Message message, Channel channel, int action, Object... args);

    String supportEntityClassName();

}
