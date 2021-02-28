package github.gx.middlewarestudy.util.defineresult;

/**
 * @program: MiddlewareStudy
 * @description: 系统相关设置
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-26 14:58
 **/
public class SystemDefine {
    // 系统相关设置
    /**
     * 死信队列 相关设置头
     * 死信 交换机
     * 死信 路由
     * 死信 默认超时时间
     */
    public static final String DEAD_EXCHANGE = "x-dead-letter-exchange";
    public static final String DEAD_ROUTING = "x-dead-letter-routing-key";
    public static final String DEAD_TIMEOUT = "x-message-ttl";
}
