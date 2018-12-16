package cn.hurrican.controller;

import cn.hurrican.consumer.model.UserEntity;
import cn.hurrican.model.AppConfig;
import cn.hurrican.model.ResMessage;
import cn.hurrican.model.UniqueKeyElement;
import cn.hurrican.mq.producer.ProducerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/3
 * @Modified 9:59
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    private static Logger logger = LogManager.getLogger(HomeController.class);

    @Autowired
    private ProducerService producerService;


    @Autowired
    @Qualifier("userEntityDirectExchange")
    private DirectExchange userEntityExchange;

    @Autowired
    @Qualifier("userEntityQueue")
    private Queue userEntityQueue;

    @Autowired
    @Qualifier("logFanoutExchange")
    private FanoutExchange logFanoutExchange;

    @Autowired
    @Qualifier("logBinding")
    private Binding logBinding;


    @RequestMapping(value = "/sendAppConfig", produces = "application/json;charset=UTF-8")
    public ResMessage sendAppConfig() {
        Random random = new Random();
        int id = random.nextInt(10000);
        AppConfig appConfig = new AppConfig();
        appConfig.setId(id);
        appConfig.setDriveName("com.mysql.jdbc.Driver");
        appConfig.setMysqlDatabase("DebugAppConfig");
        appConfig.setMysqlUsername("root");

        producerService.sendMessageToFanoutExchange("debugFanoutExchange", appConfig, "debugRoutingKey");
        System.out.println("id = " + id);
        return ResMessage.creator();
    }



    @RequestMapping(value = "/sendMessage/{id}/{nickName}", produces = "application/json;charset=UTF-8")
    public ResMessage sendMessage(@PathVariable("id") Integer id, @PathVariable("nickName") String nickName){
        UserEntity userEntity = new UserEntity();
        userEntity.setNickName(nickName);
        userEntity.setUid(id);
        userEntity.setRegisterDate(new Date());
        producerService.sendDataToDirectExchange(userEntityQueue.getName(), userEntity);
        return ResMessage.creator().msg("success");
    }


    @RequestMapping(value = "/sendLog", produces = "application/json;charset=UTF-8")
    public ResMessage sendLog(String phone){
        Random random = new Random();
        int id = random.nextInt(100);
        System.out.println("produce id = " + id);

        UniqueKeyElement element = new UniqueKeyElement();
        element.aidIs(id).platformIdIs(0).setOther("Hurrican");

        producerService.sendMessageToFanoutExchange(logFanoutExchange.getName(), element, logBinding.getRoutingKey());
        return ResMessage.creator().msg("success");
    }

}
