package github.gx.middlewarestudy.server.redisimpl;

import github.gx.middlewarestudy.dto.RedPacketDto;
import github.gx.middlewarestudy.server.IRedPacketService;
import github.gx.middlewarestudy.server.IRedService;
import github.gx.middlewarestudy.util.RedPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: MiddlewareStudy
 * @description: 红包业务 逻辑处理接口
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-20 15:30
 **/
@Service
public class RedPacketService implements IRedPacketService {

    private static final Logger log = LoggerFactory.getLogger(RedPacketService.class);

    // 存储至缓存系统 Redis 时 自定义的 Key 前缀
    // 原公司没有做这个操作，是因为 Redis 可以通过什么设置避免这个操作嘛？
    private static final String keyPrefix = "redis:red:packet:";
    // 定义 Redis 操作 Bean 组件
    // 使用 fastJson 做 解析器的 redis 组件
    private RedisTemplate redisTemplate;

    // 数据库相关操作 服务实现对象
    private IRedService redService;

    @Autowired
    public RedPacketService(RedisTemplate redisTemplate, IRedService redService) {
        this.redisTemplate = redisTemplate;
        this.redService = redService;
    }

    /**
     * 发红包相关逻辑
     * @param dto 红包信息实体类
     * @return
     * @throws Exception
     */
    @Override
    public String handOut(RedPacketDto dto) throws Exception {
        if(dto == null || dto.getTotal()<=0 || dto.getAmount() <= 0) {
            throw new Exception("系统异常-分发红包-参数不合法");
        }
        // 根据信息构造 红包信息，存入 redis 用于实现同步操作
        // 直接调用 redService 存入数据库，用于实现 有迹可循
        List<Integer> list = RedPacketUtil.divideRedPackage(dto.getAmount(), dto.getTotal());
        // 对于 Redis 其只需要记录 红包总数、 红包每部分金额 即可
        // 使用 时间戳作为红包唯一标识串
        String timeStamp = String.valueOf(System.nanoTime());
        String redId = new StringBuffer(keyPrefix).append(dto.getUserId()).append(":")
                .append(timeStamp).toString();
        // 将列表存入缓存
        redisTemplate.opsForList().leftPushAll(redId, list);
        // 将总人数存入 缓存
        String redTotalKey = redId + ":total";
        redisTemplate.opsForValue().set(redTotalKey, dto.getTotal());
        // 异步记录红包信息
        redService.recordRedPacket(dto, redId, list);
        return redId;
    }

    @Override
    public BigDecimal rob(Integer userId, String redId) throws Exception {
        return null;
    }
}
