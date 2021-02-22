package github.gx.middlewarestudy.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @program: MiddlewareStudy
 * @description: 监听 Redis 过期事件的监听对象
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-22 10:17
 **/
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    private static final Logger log = LoggerFactory.getLogger(RedisKeyExpirationListener.class);

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 获取过期 信息，并做对应的处理
        String expiredKey = message.toString();
        log.info("捕获 key 过期信息，key 内容为：{}", expiredKey);
    }
}
