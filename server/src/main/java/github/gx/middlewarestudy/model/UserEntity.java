package github.gx.middlewarestudy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: MiddlewareStudy
 * @description: 用户信息实体类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 15:02
 **/
@Data
@TableName("usertable")
public class UserEntity {
    String name;
    String age;
    String telPhone;
}
