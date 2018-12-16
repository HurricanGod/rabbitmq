package cn.hurrican.rabbitmq;

import cn.hurrican.model.AppConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/10
 * @Modified 9:50
 */
@Component
public class AppConfigListener {

    @RabbitListener(queues = "debugQueue")
    public void receiveAppConfig(@Payload AppConfig appConfig) {
        System.out.println("appConfig = " + appConfig);
        System.out.println(appConfig.getId());
    }
}
