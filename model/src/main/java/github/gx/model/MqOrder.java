package github.gx.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @program: MiddlewareStudy
 * @description: 失效订单实体类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-26 14:37
 **/
@Data
@ToString
@NoArgsConstructor
public class MqOrder {
    Integer id;
    Integer orderId;
    Date businessTime;
    String memo;
}
