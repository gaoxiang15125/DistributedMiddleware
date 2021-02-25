package github.gx.middlewarestudy.rabbitmqstudy.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.gx.middlewarestudy.rabbitmqstudy.entity.EventInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @program: MiddlewareStudy
 * @description: 广播消息模型 消费者
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-24 17:41
 **/
@Component
public class ModelConsumer {
    private static final Logger log = LoggerFactory.getLogger(ModelConsumer.class);

    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "${mq.fanout.queue.one.name}")
    public void consumeFanoutMsgOne(@Payload byte[] msg) {
        try{
            EventInfo info = objectMapper.readValue(msg, EventInfo.class);
            log.info("广播模式 第一个队列收到消息 {}", info);
        } catch (Exception e) {
            log.error("广播模式 第一个队列消费过程出现异常", e);
        }
    }

    @RabbitListener(queues = "${mq.fanout.queue.two.name}")
    public void consumeFanoutMsgTwo(@Payload byte[] msg) {
        try {
            // 监听消费队列内容
            EventInfo info = objectMapper.readValue(msg, EventInfo.class);
            log.info("消息模型 第二个队列收到消息 {}", info);
        } catch (Exception e) {
            log.error("第二个消息队列接受消息过程中出现异常", e);
        }
    }
}
