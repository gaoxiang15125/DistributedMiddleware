package github.gx.middlewarestudy;

import github.gx.middlewarestudy.server.redisimpl.RedPacketService;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @program: MiddlewareStudy
 * @description: Spring boot 项目启动类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 10:22
 **/
@SpringBootApplication
@MapperScan("github.gx.mapper")
public class MainApplication {
    static Logger logger = LoggerFactory.getLogger(MainApplication.class);
    public static void main(String[] args) {
        logger.info(" Spring boot 启动了，看到了嘛");
        ApplicationContext context = SpringApplication.run(MainApplication.class, args);
        RedPacketService redPacketService = context.getBean(RedPacketService.class);
        redPacketService.testExpiredKey();
    }
}
