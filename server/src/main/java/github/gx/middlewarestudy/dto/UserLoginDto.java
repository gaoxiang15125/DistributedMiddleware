package github.gx.middlewarestudy.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @program: MiddlewareStudy
 * @description: 用户登录相关信息存储类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-25 17:34
 **/
@Data
@ToString
public class UserLoginDto {
    String userName;
    String password;
    Integer userId;
}
