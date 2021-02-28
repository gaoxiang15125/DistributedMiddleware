package github.gx.middlewarestudy.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @program: MiddlewareStudy
 * @description: 用户下单相关信息实体类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-27 15:15
 **/
@Data
@ToString
public class UserOrderDto {

    //订单编号
    String orderNo;
    // 用户 id
    Integer userId;
    // 并不是真的做业务 不整这么复杂了
}
