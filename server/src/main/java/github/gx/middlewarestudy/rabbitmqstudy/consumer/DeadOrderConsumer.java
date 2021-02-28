package github.gx.middlewarestudy.rabbitmqstudy.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.gx.mapper.UserOrderMapper;
import github.gx.middlewarestudy.server.rabbitmqimpl.DeadUserOrderService;
import github.gx.model.UserOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * @program: MiddlewareStudy
 * @description: 死信队列消费者
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-28 16:25
 **/
public class DeadOrderConsumer {

    // 与之相对应的 应该存在一个正常的订单完成队列，记录正常的订单完成流程
    private static final Logger log = LoggerFactory.getLogger(DeadOrderConsumer.class);

    ObjectMapper objectMapper;
    UserOrderMapper userOrderMapper;
    DeadUserOrderService deadUserOrderService;

    @Autowired
    public DeadOrderConsumer(ObjectMapper objectMapper, UserOrderMapper userOrderMapper, DeadUserOrderService deadUserOrderService) {
        this.objectMapper = objectMapper;
        this.userOrderMapper = userOrderMapper;
        this.deadUserOrderService = deadUserOrderService;
    }

    /**
     * 死信队列消费者 主要处理消息，将未按时支付的订单设置为超时
     * 订单的超时时间应该从配置文件获取并设置
     */
    @RabbitListener(queues = "${mq.order.producer.order.real.queue.name}", containerFactory = "singleListenerContainer")
    public void consumeMsg(@Payload Integer orderId) {
        try{
            log.info("死信队列消费者接收到对应消息 orderId = {}", orderId);

            UserOrder userOrder = userOrderMapper.selectById(orderId);
            if(userOrder==null){
                throw new Exception("未能从数据库查询到目标订单相关信息");
            }
            deadUserOrderService.updateUserOrderRecord(userOrder);
        } catch (Exception e) {
            log.error("死信队列消费超时订单消息过程出现错误 ", e);
        }
    }
}
