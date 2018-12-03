package cn.hurrican.model;

import cn.hurrican.rabbitmq.AsyncMessageAction;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/3
 * @Modified 9:56
 */
@Data
@ToString
public class UserEntity implements AsyncMessageAction{

    private int action = INSERT;

    private Integer uid;

    private String openid;

    private String nickName;

    private String headImg;

    private String phone;

    private Date registerDate;


    @Override
    public int getAction() {
        return action;
    }
}
