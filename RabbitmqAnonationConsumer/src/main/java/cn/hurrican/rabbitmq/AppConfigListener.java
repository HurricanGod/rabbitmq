package cn.hurrican.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/10
 * @Modified 9:50
 */
@Component
public class AppConfigListener {

    @RabbitListener(queues = "appConfigQueue")
    public void receiveAppConfig(){

    }
}
