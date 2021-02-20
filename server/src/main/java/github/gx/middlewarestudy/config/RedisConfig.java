package github.gx.middlewarestudy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @program: MiddlewareStudy
 * @description: Redis Bean 相关配置信息
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 18:33
 **/
@Configuration
public class RedisConfig {
    // Redis 链接工厂
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    // RedisTemplate 自定义配置
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        // 使用 fastJson 实现序列化
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // TODO 按照理解为 redisTemplate 设置序列化类型
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 按道理存在使用类检索 hash 表的可能
        return redisTemplate;
    }

    // StringRedisTemplate 组件配置
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        // 是不是可以使用 String 作为类处理操作呢 ？
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }
}
