package github.gx.model.lock;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @program: MiddlewareStudy
 * @description: 用户账户实体类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-28 20:39
 **/
@Data
@ToString
public class UserAccount {
    // 主键 id
    Integer id;
    // 用户账户 id
    Integer userId;
    // 账户余额
    BigDecimal amount;
    // 版本号 怎么可能是记录在实体类里的？乐观锁不是基于 数据库实现的嘛 ？？
    /**
     * 对于 mybatis-plus 自有一套其乐观锁实现方案
     * OptimisticLockInnerInterceptor
     * 在字段上加上 @Version
     * 支持的数据类型 int Integer long Long Date
     * 仅支持 updateById(id)  update(entity,wrapper) 方法
     * 在 update(entity,wrapper)方法下 wrapper 不能复用 ？？ 什么意思
     */
    @Version
    Integer version;
    // 是否为 有效账户
    Byte isActive;
}
