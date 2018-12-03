package cn.hurrican.model;

import cn.hurrican.rabbitmq.AsyncMessageAction;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/3
 * @Modified 9:47
 */
@Data
@ToString
public class TestMqEntity implements AsyncMessageAction{

    private int action;

    private Integer id;

    private Integer uid;

    private String description;

    private Date createTime;

    private Double score;

    @Override
    public int getAction() {
        return action;
    }
}
