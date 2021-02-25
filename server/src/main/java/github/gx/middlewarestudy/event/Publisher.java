package github.gx.middlewarestudy.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @program: MiddlewareStudy
 * @description: 消息发布者
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-22 14:30
 **/
// 只需要获取 ApplicationPublisher ? 不需要进行绑定操作 ？
@Component
public class Publisher {

    private static final Logger log = LoggerFactory.getLogger(Publisher.class);

    private ApplicationEventPublisher publisher;

    @Autowired
    public Publisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    // 发送信息
    public void sendMsg() throws Exception {
        // 构造实体类，用于 发送信息
        LoginEvent event = new LoginEvent(this,"忍呢哪里去了", "2020/02/22", "localhost");
        publisher.publishEvent(event);
        log.info("Spring 事件驱动有个大问题，没有绑定操作，复杂的时候不好处理 {}",event);
    }

}
