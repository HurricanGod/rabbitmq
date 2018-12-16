package cn.hurrican.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/12/10
 * @Modified 9:52
 */
@Data
@ToString
@Accessors(chain = true)
public class AppConfig {

    private Integer id;

    private String mysqlHost;

    private String mysqlDatabase;

    private String mysqlUsername;

    private String mysqlPassword;

    private String driveName;

}
