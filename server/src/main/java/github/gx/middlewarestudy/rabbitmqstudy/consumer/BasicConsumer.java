package github.gx.middlewarestudy.rabbitmqstudy.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.gx.middlewarestudy.rabbitmqstudy.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @program: MiddlewareStudy
 * @description: 基础消费者实现类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-23 19:49
 **/
@Component
public class BasicConsumer {

    private static final Logger log = LoggerFactory.getLogger(BasicConsumer.class);

    ObjectMapper objectMapper;

    @Autowired
    public BasicConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 创建 消息消费者
     */
    // 通过注解注册消息处理方法
//    @RabbitListener(queues = "${mq.basic.info.queue.name}")
//    public void consumeMsg(@Payload byte[] msg) {
//        try {
//            // 尝试对接受到的消息进行转化
//            String message = new String(msg,"utf-8");
//            log.info("接收到的基本消息内容为：{}", message);
//        } catch (Exception e) {
//            log.error("消息消费者执行过程出现错误", e);
//        }
//    }

    /**
     * 监听并消费队列中的消息， object 消息类型
     */
    @RabbitListener(queues = "${ouhou.object.info.queue.name}")
    public void consumeObjectMsg(@Payload Object person) {
        try{
            log.info("对象Person 消费者消费的信息为 {}",person);
        } catch (Exception e) {
            log.error("基本消息被消费过程出现异常", e);
        }
    }
}
