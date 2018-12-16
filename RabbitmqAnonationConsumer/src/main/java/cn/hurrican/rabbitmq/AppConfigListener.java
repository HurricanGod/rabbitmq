package cn.hurrican.rabbitmq;

import cn.hurrican.model.AppConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/10
 * @Modified 9:50
 */
@Component
public class AppConfigListener {

    @RabbitListener(queues = "debugQueue")
    public void receiveAppConfig(@Payload AppConfig appConfig, Channel channel, @Headers Map<String, Object> headers) {
        System.out.println("appConfig = " + appConfig);
        if (headers != null) {
            headers.forEach((k, v) -> System.out.printf("key = %s\tvalue = %s\n", k, v));
            try {
                channel.basicAck((Long) headers.get("amqp_deliveryTag"), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
