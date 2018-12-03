package cn.hurrican.mq.consumer;

import cn.hurrican.model.TestMqEntity;
import cn.hurrican.rabbitmq.MessageConsumerHandler;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/23
 * @Modified 11:00
 */
@Service
public class TestMessageConsumerHandlerImpl implements MessageConsumerHandler {
    @Override
    public String supportEntityClassName() {
        return TestMqEntity.class.getName();
    }

    @Override
    public Object invoke(Message message, Channel channel, int action, Object... args) {
        return null;
    }
}
