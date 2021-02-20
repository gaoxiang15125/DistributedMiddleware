package github.gx.middlewarestudy.server;

import github.gx.middlewarestudy.dto.RedPacketDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: MiddlewareStudy
 * @description: 红包业务 逻辑处理过程 数据记录接口-异步实现
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-20 13:58
 **/
public interface IRedService {
    // 记录发红包时红包的全局唯一标识串、随机金额列表和个数等信息
    void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception;
    // 记录抢红八十用户抢到的红包金额等信息
    void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception;
}
