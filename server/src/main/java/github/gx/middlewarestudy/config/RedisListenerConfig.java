package github.gx.middlewarestudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @program: MiddlewareStudy
 * @description: redis key 过期监听器
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-22 10:12
 **/
@Configuration
public class RedisListenerConfig {
    // redis 为什么这么叼，还能反过来通知 Spring
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }
}
