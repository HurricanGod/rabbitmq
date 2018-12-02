package cn.hurrican.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;
import cn.hurrican.rabbitmq.MessageConsumerHandler;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/11/30
 * @Modified 17:25
 */
@Service
public class UserMessageConsumerHandlerImpl implements MessageConsumerHandler {

    @Override
    public String supportEntityClassName() {
        return null;
    }

    @Override
    public Object invoke(Message message, Channel channel, int action, Object... args) {
        return null;
    }
}
