package github.gx.middlewarestudy.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @program: MiddlewareStudy
 * @description: 消息消费者
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-22 14:30
 **/
@Component
@EnableAsync
public class Consumer implements ApplicationListener<LoginEvent> {

    // 日志对象
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    @Override
    @Async
    // 使用注解 异步执行消息处理方法
    public void onApplicationEvent(LoginEvent event) {
        // 对于接收到的消息，打印消息内容即可
        log.info("接收到消息，消息内容为 {}", event.toString());

    }
}
