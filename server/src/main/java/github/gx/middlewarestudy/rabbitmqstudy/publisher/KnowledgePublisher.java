package github.gx.middlewarestudy.rabbitmqstudy.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.gx.middlewarestudy.rabbitmqstudy.entity.KnowledgeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @program: MiddlewareStudy
 * @description: 自定义消息返回类型
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-25 14:04
 **/
@Component
public class KnowledgePublisher {
    private static final Logger log = LoggerFactory.getLogger(KnowledgePublisher.class);

    ObjectMapper objectMapper;

    Environment environment;

    RabbitTemplate rabbitTemplate;

    @Autowired
    public KnowledgePublisher(ObjectMapper objectMapper, Environment environment, RabbitTemplate rabbitTemplate) {
        this.objectMapper= objectMapper;
        this.environment = environment;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 如何指定 Auto 模式
     */
    public void sendAutoMsg(KnowledgeInfo knowledgeInfo) {
        try {
            if(knowledgeInfo == null) {
                return;
            }
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(environment.getProperty("mq.auto.knowledge.exchange.name"));
            rabbitTemplate.setRoutingKey(environment.getProperty("mq.auto.knowledge.routing.name"));
            // 创建消息 并设置对应的持久话策略
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(knowledgeInfo))
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            rabbitTemplate.convertAndSend(message);
            log.info("使用Auto 模式 发送消息内容为: {}", knowledgeInfo);
        } catch(Exception e) {
            log.error("使用 Auto 模式发送消息出现错误", e);
        }
    }
}
