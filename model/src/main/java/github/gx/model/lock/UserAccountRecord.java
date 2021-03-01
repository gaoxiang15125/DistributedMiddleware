package github.gx.model.lock;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: MiddlewareStudy
 * @description: 用户提现时金额记录实体类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-28 20:45
 **/
@Data
@ToString
public class UserAccountRecord {
    // 主键 id
    @TableId(type = IdType.AUTO)
    Integer id;
    // 账户记录主键 id
    Integer accountId;
    // 提现金额
    BigDecimal money;
    // 提现成功时间
    Date createTime;
}
