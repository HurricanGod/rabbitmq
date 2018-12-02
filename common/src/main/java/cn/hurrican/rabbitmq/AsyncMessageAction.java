package cn.hurrican.rabbitmq;

/**
 *  使用 Rabbitmq 进行异步数据库操作的实体需要实现该接口
 */
public interface AsyncMessageAction {

    /**
     * 往数据库插入操作
     */
    int INSERT = 0;

    /**
     * 更新数据
     */
    int UPDATE = 1;

    /**
     * 删除数据
     */
    int DELETE = 2;


    int getAction();
}
