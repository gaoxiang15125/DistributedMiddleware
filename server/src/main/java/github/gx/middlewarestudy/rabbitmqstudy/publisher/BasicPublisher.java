package github.gx.middlewarestudy.rabbitmqstudy.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;;
import github.gx.middlewarestudy.rabbitmqstudy.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * @program: MiddlewareStudy
 * @description: 基本消息模型-生产者
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-23 19:21
 **/
@Component
public class BasicPublisher {
    // 日志实例对象
    private final static Logger log = LoggerFactory.getLogger(BasicPublisher.class);

    ObjectMapper objectMapper;

    RabbitTemplate rabbitTemplate;

    Environment environment;

    @Autowired
    public BasicPublisher(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, Environment environment) {
        this.environment = environment;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 发送消息实现方法 绑定关系也是使用 Bean 进行实现的 ？？
     */
    public void sendMsg(String message) {
        if (!StringUtils.isEmpty(message)){
            try {
                //定义消息传输的格式为JSON字符串格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定消息模型中的交换机
                rabbitTemplate.setExchange(environment.getProperty("mq.basic.info.exchange.name"));
                        //指定消息模型中的路由
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.basic.info.routing.name"));
                //将字符串值转化为待发送的消息，即一串二进制的数据流
                Message msg= MessageBuilder.withBody(message.getBytes("utf-8")).
                        build();
                //转化并发送消息
                rabbitTemplate.convertAndSend(msg);
                //打印日志信息
                log.info("基本消息模型-生产者-发送消息：{} ",message);
            }catch (Exception e){
                log.error("基本消息模型-生产者-发送消息发生异常：{} ",message,e.
                        fillInStackTrace());
            }
        }
    }

    /**
     * 发送对象型消息实现方法
     */
    public void sendObjectMsg(Person person) {
        // 判断对象是否为空
        if(person == null) {
            return;
        }
        try {
            // 设置消息传输过程中的格式为 json
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            // 指定交换机
            rabbitTemplate.setExchange(environment.getProperty("ouhou.object.info.exchange.name"));
            // 指定路由
            rabbitTemplate.setRoutingKey(environment.getProperty("ouhou.object.info.routing.name"));
            //采用convertAndSend方法即可发送消息
            rabbitTemplate.convertAndSend(person, message -> {
                //获取消息的属性
                MessageProperties messageProperties=message.getMessageProperties();
                //设置消息的持久化模式
                messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                //设置消息的类型（在这里指定消息类型为Person类型）
                // 从网页版的消息队列就能发现此处的设置  呵呵哒
                messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Person.class);
                //返回消息实例
                return message;
            });
            //打印日志信息
            log.info("基本消息模型-生产者-发送对象类型的消息：{} ",person);
        } catch (Exception e) {
            log.error("发送对向型消息过程出现错误:", e);
        }
    }
}