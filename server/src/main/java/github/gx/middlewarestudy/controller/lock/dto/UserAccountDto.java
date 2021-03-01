package github.gx.middlewarestudy.controller.lock.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @program: MiddlewareStudy
 * @description: 用于存取款操作的实体类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-28 20:59
 **/
@Data
@ToString
public class UserAccountDto {
    Integer userId; // 用户账户Id
    BigDecimal amount;  // 提现金额
}
