package github.gx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;

import java.util.Date;

/**
 * @program: MiddlewareStudy
 * @description: 用户下单实体类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-26 14:30
 **/
@Data
@NoArgsConstructor
public class UserOrder {

    // 主键
    @TableId(type = IdType.AUTO)
    Integer id;
    // 订单号
    String orderNo;
    // 用户 id
    Integer userId;
    // 支付状态 用于判断订单是否超时
    // 0 完成支付 1 等待支付
    Integer status;
    // 订单是否有效 0 表示失效 1表示有效
    Integer isActive;
    // 订单创建时间
    Date createTime;
    // 订单更新时间
    Date updateTime;
}
