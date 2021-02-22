package github.gx.middlewarestudy.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: MiddlewareStudy
 * @description: 红包随机金额存储实体类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 21:01
 **/
@TableName("red_detail")
@Data
public class RedDetail {

    @TableId
    int id;
    /**
     * 红包记录 ID
     */
    String recordId;
    // TODO 没看出来这个红包怎么跟别的红包关联在一起的
    /**
     * 每个红包随机金额
     */
    BigDecimal amount;

    /**
     * 是否有效 1 有效 0 无效
     */
    boolean isActive;

    /**
     * 创建时间
     */
    Date createTime;
}
