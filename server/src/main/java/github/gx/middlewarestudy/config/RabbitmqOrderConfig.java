package github.gx.middlewarestudy.config;

import github.gx.middlewarestudy.util.defineresult.SystemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: MiddlewareStudy
 * @description: 消息队列订单业务 相关队列配置信息
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-26 14:46
 **/
@Configuration
public class RabbitmqOrderConfig {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfig.class);

    // 这些所谓的工厂实例，应该是 Spring 那个依赖提供的吧
    private CachingConnectionFactory connectionFactory;
    // 自动装配消息监听器所在容器工厂配置类实例
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
    // 用于读取配置相关信息
    Environment environment;

    @Autowired
    public RabbitmqOrderConfig(CachingConnectionFactory connectionFactory,
                               SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer,
                               Environment environment) {
        this.connectionFactory = connectionFactory;
        this.factoryConfigurer = factoryConfigurer;
        this.environment = environment;
    }

    /**
     * 用户下单支付超时 RabbitMQ 死信队列消息模型
     * @return
     */
    @Bean
    public Queue orderDeadQueue() {
        Map<String, Object> args = new HashMap<>();
        // 添加死信队列 相关的设置
        args.put(SystemDefine.DEAD_EXCHANGE, environment.getProperty("mq.order.dead.exchange.name"));
        args.put(SystemDefine.DEAD_ROUTING, environment.getProperty("mq.order.dead.routing.name"));
        // 设定延迟时间 单位为: ms
        args.put(SystemDefine.DEAD_TIMEOUT, 1000);
        return new Queue(environment.getProperty("mq.order.dead.queue.name"), true, false, false, args);
    }

    // 创建 基本消息模型 的基本交换机
    @Bean
    public TopicExchange orderProduceExchange() {
        return new TopicExchange(environment.getProperty("mq.order.producer.order.exchange.name"), true, false);
    }

    // 创建 绑定关系 基本路由交换机绑定 死信队列
    // 死信路由交换机 绑定 普通队列
    @Bean
    public Binding orderProducerBinding() {
        return BindingBuilder.bind(orderDeadQueue()).to(orderProduceExchange())
                .with(environment.getProperty("mq.order.producer.order.routing.name"));
    }

    // 面向消费者的队列
    @Bean
    public Queue realOrderConsumerQueue() {
        return new Queue(environment.getProperty("mq.order.producer.order.real.queue.name"), true);
    }

    /**
     *  创建 死信交换机 与 死信路由
     */
    @Bean
    public TopicExchange basicOrderDeadExchange() {
        return new TopicExchange(environment.getProperty("mq.order.dead.exchange.name"), true, false);
    }

    /**
     * 创建死信队列与普通队列之间的绑定关系
     */

    public Binding basicOrderDeadBinding() {
        return BindingBuilder.bind(realOrderConsumerQueue()).to(basicOrderDeadExchange()).with(environment.getProperty("mq.order.dead.routing.name"));
    }


}

