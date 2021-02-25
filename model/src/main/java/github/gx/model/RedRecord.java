package github.gx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: MiddlewareStudy
 * @description: 发红包记录实体类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 21:03
 **/
@TableName("red_record")
@Data
public class RedRecord {

    @TableId(type = IdType.AUTO)
    int id;
    /**
     * 用户 ID
     */
    int userId;
    /**
     * 红包唯一识别串
     */
    String redPacket;
    /**
     * 总人数
     */
    int total;
    /**
     * 总金额
     */
    BigDecimal amount;
    /**
     * 是否有效
     */
    boolean isActive;
    /**
     * 红包创建时间
     */
    Date createTime;
}
