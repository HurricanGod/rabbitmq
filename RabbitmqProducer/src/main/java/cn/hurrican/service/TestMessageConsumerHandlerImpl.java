package cn.hurrican.service;

import cn.hurrican.rabbitmq.MessageConsumerHandler;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

@Service
public class TestMessageConsumerHandlerImpl implements MessageConsumerHandler {
    @Override
    public String supportEntityClassName() {
        return null;
    }

    @Override
    public Object invoke(Message message, Channel channel, int action, Object... args) {
        return null;
    }
}
