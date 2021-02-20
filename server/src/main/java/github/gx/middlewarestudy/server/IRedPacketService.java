package github.gx.middlewarestudy.server;

import github.gx.middlewarestudy.dto.RedPacketDto;

import java.math.BigDecimal;

/**
 * @program: MiddlewareStudy
 * @description: 红包业务逻辑处理接口
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-20 13:38
 **/
public interface IRedPacketService {

    // 发红包核心业务逻辑
    String handOut(RedPacketDto dto) throws Exception;
    // 抢红包
    BigDecimal rob(Integer userId, String redId) throws Exception;
}
