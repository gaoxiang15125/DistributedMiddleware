package github.gx.middlewarestudy.rabbitmqstudy.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.gx.middlewarestudy.rabbitmqstudy.entity.EventInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @program: MiddlewareStudy
 * @description: 消息模型 生产者
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-24 17:25
 **/
@Component
public class ModelPublisher {

    private static final Logger log = LoggerFactory.getLogger(ModelPublisher.class);

    ObjectMapper objectMapper;
    RabbitTemplate rabbitTemplate;
    Environment environment;

    @Autowired
    public ModelPublisher(ObjectMapper objectMapper,
                          RabbitTemplate rabbitTemplate,
                          Environment environment) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.environment = environment;
    }

    public void sendMsg(EventInfo info) {
        if(info!=null) {
            try{
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.fanout.exchange.name"));
                Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(info)).build();
                // 发送消息
                rabbitTemplate.convertAndSend(message);
                log.info("广播消息发送成功 {}", message);
            } catch(Exception e) {
                log.error("广播消息过程中出现错误 ", e);
            }
        }
    }
}
