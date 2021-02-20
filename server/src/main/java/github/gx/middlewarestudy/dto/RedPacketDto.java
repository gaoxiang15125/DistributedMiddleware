package github.gx.middlewarestudy.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

/**
 * @program: MiddlewareStudy
 * @description: 红包信息入参对象
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-20 13:26
 **/
@Data
@ToString
public class RedPacketDto {

    // 之前看到的都是通过 String 传递参数，这里为什么改为了 对象
    // 现有的 框架会帮我们完成对应的解析操作嘛？？
    private Integer userId;
    @NonNull
    private Integer total;
    @NonNull
    private Integer amount;
}
