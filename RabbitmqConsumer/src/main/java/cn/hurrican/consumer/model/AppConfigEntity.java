package cn.hurrican.consumer.model;

import cn.hurrican.rabbitmq.AsyncMessageAction;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/3
 * @Modified 9:53
 */
@Data
@ToString
public class AppConfigEntity implements AsyncMessageAction{

    private int action = INSERT;

    private String logDir;

    private Integer concurrentCount;

    private Integer maxConcurrentCount;

    private Date lastUpdateTime;


    @Override
    public int getAction() {
        return action;
    }
}
