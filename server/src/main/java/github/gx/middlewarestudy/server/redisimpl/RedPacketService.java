package github.gx.middlewarestudy.server.redisimpl;

import github.gx.middlewarestudy.dto.RedPacketDto;
import github.gx.middlewarestudy.server.IRedPacketService;
import github.gx.middlewarestudy.server.IRedService;
import github.gx.middlewarestudy.util.RedPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    // 由于 redis 是单线程，所以红包个数是一定的，问题在于防止用户抢了多次红包
    @Override
    public BigDecimal rob(Integer userId, String redId) throws Exception {
        //做两件事情、检查 Redis 判断是否由可用的红包，将红包记录写入数据库
        String robId = redId + ":rob";
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object obj = valueOperations.get(redId + userId + ":rob");
        if(obj != null) {
            // 已经抢过红包，返回红包金额
            return new BigDecimal(obj.toString());
        }
        // 点红包业务
        Boolean res = click(redId);
        // 事实上就算红包总数出现问题也不影响，因为队列里红包的数量是固定的
        String lockId = userId + redId + ":lock";
        // 原子的写入锁，保证一个用户仅有一次抢红包操作
        boolean lock = valueOperations.setIfAbsent(lockId, redId);
        // 设置过期时间
        redisTemplate.expire(lockId, 2L, TimeUnit.HOURS);

        try {
            //表示当前线程获取到了该分布式锁
            if (lock) {
                //开始执行后续的业务逻辑-注释同前面rob()的注释
                Object value=redisTemplate.opsForList().rightPop(redId);
                if (value!=null){
                    //红包个数减1
                    String redTotalKey = redId+":total";
                    Integer currTotal=valueOperations.get(redTotalKey)!=null?
                            (Integer) valueOperations.get(redTotalKey) : 0;
                    valueOperations.set(redTotalKey,currTotal-1);
                    //将红包金额返回给用户的同时，将抢红包记录存入数据库与缓存中
                    BigDecimal result = new BigDecimal(value.toString()).
                            divide(new BigDecimal(100));
                    redService.recordRobRedPacket(userId,redId,new BigDecimal
                            (value.toString()));
                    //将当前用户抢到的红包记录存入缓存中，表示当前用户已经抢过该红包了
                    valueOperations.set(redId+userId+":rob",result,24L,
                            TimeUnit.HOURS);
                    //打印抢到的红包金额信息
                    log.info("当前用户抢到红包了：userId={} key={} 金额={} ",
                            userId,redId,result);
                    return result;
                }}
        }catch (Exception e){
            throw new Exception("系统异常-抢红包-加分布式锁失败!");
        }
        return null;
    }

    /**
     * 点红包业务 通过锁获取红包
     * @param redId
     * @return
     * @throws Exception
     */
    private Boolean click(String redId) throws Exception {
        ValueOperations valueOperations =redisTemplate.opsForValue();
        String redTotalKey = redId + ":total";
        Object total = valueOperations.get(redTotalKey);
        if(total!=null && Integer.valueOf(total.toString())>0) {
            return true;
        }
        return false;
    }

    public void testExpiredKey() {
        redisTemplate.opsForValue().set("吼吼","houhou");
        redisTemplate.expire("吼吼",2l, TimeUnit.SECONDS);
    }
}
