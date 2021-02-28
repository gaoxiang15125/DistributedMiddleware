package github.gx.middlewarestudy.rabbitmqstudy.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @program: MiddlewareStudy
 * @description: 死信队列订单消息发布者
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-28 14:52
 **/
@Component
public class DeadOrderPublisher {
    // 日志对象
    private static final Logger log = LoggerFactory.getLogger(DeadOrderPublisher.class);

    // 创建相关操作实例
    Environment environment;
    RabbitTemplate rabbitTemplate;
    ObjectMapper objectMapper;

    @Autowired
    public DeadOrderPublisher(Environment environment, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.environment= environment;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 将用户下单消息提供到私信队列，到达时间后 做对应的处理操作
     */
    public void sendMsg(Integer orderId) {
        try{
            // 这里设置消息传输格式 是不是要与消费者使用的 factory 生成策略保持一致？
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            // 设置交换机 路由 编写消息发送方法
            rabbitTemplate.setExchange(environment.getProperty("mq.order.producer.order.exchange.name"));
            rabbitTemplate.setRoutingKey(environment.getProperty("mq.order.producer.order.routing.name"));

            // 设置发送消息实现
            rabbitTemplate.convertAndSend(orderId, new MessagePostProcessor() {

                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    // 对消息进行组装，构造消息内容
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Integer.class);
                    return message;
                }
            });
            log.info("用户下单模块 - 订单id 进入死信队列 orderId={}", orderId);

        } catch(Exception e) {
            // 处理异常相关日志
            log.error("用户下单模块 消息进入死信队列过程出现错误", e);
        }
    }
}
