package cn.hurrican.mq.consumer;

import cn.hurrican.model.AppConfigEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;
import cn.hurrican.rabbitmq.MessageConsumerHandler;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/11/30
 * @Modified 17:24
 */
@Service
public class AppConfigMessageConsumerHandlerImpl implements MessageConsumerHandler {


    @Override
    public Object invoke(Message message, Channel channel, int action, Object... args) {
        return null;
    }

    @Override
    public String supportEntityClassName() {
        return AppConfigEntity.class.getName();
    }
}
