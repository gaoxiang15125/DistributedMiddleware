package github.gx.middlewarestudy.model;

import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: MiddlewareStudy
 * @description: 红包分配金额明细实体类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 21:05
 **/
public class RedRobRecord {

    @TableId
    int id;
    /**
     * 用户 ID
     */
    int userId;
    /**
     * 红包唯一标识串
     */
    String redPacket;
    /**
     * 抢到的红包金额
     */
    BigDecimal amount;
    /**
     * 抢到红包的时间
     */
    Date robTime;
    /**
     * 是否有效
     */
    boolean isActive;
}
