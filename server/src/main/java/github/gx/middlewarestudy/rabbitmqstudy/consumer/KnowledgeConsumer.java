package github.gx.middlewarestudy.rabbitmqstudy.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.gx.middlewarestudy.rabbitmqstudy.entity.KnowledgeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @program: MiddlewareStudy
 * @description: 自动消费者
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-25 14:18
 **/
@Component
public class KnowledgeConsumer {
    static final Logger log = LoggerFactory.getLogger(KnowledgeConsumer.class);

    ObjectMapper objectMapper;

    @Autowired
    public KnowledgeConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${mq.auto.knowledge.queue.name}", containerFactory = "singleListenerContainerAuto")
    public void consumeAutoMsg(@Payload byte[] msg) {
        try{
            KnowledgeInfo info = objectMapper.readValue(msg, KnowledgeInfo.class);
            log.info("消费者成功回去消费信息，内容为：{}", info);
        } catch(Exception e) {
            log.error("创建基于 auto 消息处理的消费者出现异常",e);
        }
    }

}
