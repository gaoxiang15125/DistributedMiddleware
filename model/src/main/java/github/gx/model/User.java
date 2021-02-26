package github.gx.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @program: MiddlewareStudy
 * @description: 用户相关信息表
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-25 17:31
 **/
@Data
@ToString
public class User {

    private Integer id;
    private String userName;
    private String password;
    private Date creatTime;
}
