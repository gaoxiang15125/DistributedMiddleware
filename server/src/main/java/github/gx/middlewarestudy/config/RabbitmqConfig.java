package github.gx.middlewarestudy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * @program: MiddlewareStudy
 * @description: 自定义消息队列对象配置信息 应该是方便进行定制吧
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-22 16:10
 **/
@Configuration
public class RabbitmqConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfig.class);

    // 这些所谓的工厂实例，应该是 Spring 那个依赖提供的吧
    private CachingConnectionFactory connectionFactory;
    // 自动装配消息监听器所在容器工厂配置类实例
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
    // 用于读取配置相关信息
    Environment environment;

    @Autowired
    public RabbitmqConfig(CachingConnectionFactory cachingConnectionFactory,
                          SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer,
                          Environment environment) {
        this.connectionFactory = cachingConnectionFactory;
        this.factoryConfigurer = factoryConfigurer;
        this.environment = environment;
    }

    /**
     * 下面为单一消费者实例的配置
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        //定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂所用的实例
        factory.setConnectionFactory(connectionFactory);
        //设置消息在传输中的格式，在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置并发消费者实例的初始数量。在这里为1个
        factory.setConcurrentConsumers(1);
        //设置并发消费者实例的最大数量。在这里为1个
        factory.setMaxConcurrentConsumers(1);
        //设置并发消费者实例中每个实例拉取的消息数量-在这里为1个
        factory.setPrefetchCount(1);
        return factory;
    }

    /**
     *下面为多个消费者实例的配置，主要是针对高并发业务场景的配置
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        //定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂所用的实例
        factoryConfigurer.configure(factory,connectionFactory);
        //设置消息在传输中的格式。在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置消息的确认消费模式。在这里为NONE，表示不需要确认消费
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        //设置并发消费者实例的初始数量。在这里为10个
        factory.setConcurrentConsumers(10);
        //设置并发消费者实例的最大数量。在这里为15个
        factory.setMaxConcurrentConsumers(15);
        //设置并发消费者实例中每个实例拉取的消息数量。在这里为10个
        factory.setPrefetchCount(10);
        return factory;
    }

    //自定义配置RabbitMQ发送消息的操作组件RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(){
        //设置“发送消息后进行确认”
        connectionFactory.setPublisherConfirms(true);
        //设置“发送消息后返回确认信息”
        connectionFactory.setPublisherReturns(true);
        //构造发送消息组件实例对象
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        //发送消息后，如果发送成功，则输出“消息发送成功”的反馈信息
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            public void confirm(CorrelationData correlationData, boolean
                    ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",
                        correlationData,ack,cause);
            }
        });
        //发送消息后，如果发送失败，则输出“消息发送失败-消息丢失”的反馈信息
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}), replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);

            }
        });
        //最终返回RabbitMQ的操作组件实例RabbitTemplate
        return rabbitTemplate;
    }

    /**
     * 创建简单的消息模型 队列 交换机 路由
     * 创建队列
     */
    @Bean(name = "basicQueue")
    public Queue basicQueue() {
        return new Queue(Objects.requireNonNull(environment.getProperty("mq.basic.info.queue.name")), true);
    }

    /**
     * 创建交换机
     */
    @Bean
    public DirectExchange basicExchange() {
        return new DirectExchange(environment.getProperty("mq.basic.info.exchange.name"));
    }

    /**
     * 创建绑定关系
     */
    @Bean
    public Binding basicBinding() {
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(environment.getProperty("mq.basic.info.routing.name"));
    }

    /**
     * 创建用于传输对象的队列、路由 并进行绑定
     */
    @Bean(name = "objectQueue")
    public Queue objectQueue(){
        String queueName = environment.getProperty("ouhou.object.info.queue.name");
        System.out.println(queueName);
        return new Queue(queueName, true);
    }

    @Bean
    public DirectExchange objectExchange() {
        return new DirectExchange(environment.getProperty("ouhou.object.info.exchange.name"),true,false);
    }

    /**
     * 创建绑定关系
     */
    @Bean
    public Binding objectBinding() {
        return BindingBuilder.bind(objectQueue()).to(objectExchange()).with(environment.getProperty("ouhou.object.info.routing.name"));
    }

    /**
     * 创建 基于 FanoutExchange 消息模型的 交换机
     */
    @Bean(name = "fanoutQueueOne")
    public Queue fanoutQueueOne() {
        return new Queue(environment.getProperty("mq.fanout.queue.one.name"),true);
    }
    @Bean(name = "fanoutQueueTwo")
    public Queue fanoutQueueTwo() {
        return new Queue(environment.getProperty("mq.fanout.queue.two.name"), true);
    }

    /**
     * 创建交换机、不再经过路由
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(environment.getProperty("mq.fanout.exchange.name"));
    }

    /**
     * 分别为两个队列指定各自的绑定关系
     */
    @Bean
    public Binding fanoutBindingOne() {
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBindingTwo() {
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }

    @Bean(name = "singleListenerContainerAuto")
    public SimpleRabbitListenerContainerFactory listenerContainerAuto() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        // 设置消息接受回调方式 为 自动
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        // 返回 监听器容器 工厂实例
        return factory;
    }

    // 创建自动队列、 交换机、并确定绑定关系
    @Bean(name = "autoQueue")
    public Queue autoQueue() {
        return new Queue(environment.getProperty("mq.auto.knowledge.queue.name"), true);
    }

    @Bean
    public DirectExchange autoExchange() {
        return new DirectExchange(environment.getProperty("mq.auto.knowledge.exchange.name"), true, false);
    }

    // 突然心生疑惑三者的关系是怎么样的呢 ？
    @Bean
    public Binding autoBinding() {
        // 创建并返回队列交换机与路由绑定实例
        return BindingBuilder.bind(autoQueue()).to(autoExchange()).with(environment.getProperty("mq.auto.knowledge.routing.name"));
    }

    /**
     * 用户登录相关 组件信息
//     */
//    @Bean(name = "loginQueue")
//    public Queue loginQueue() {
//        return new Queue(environment.getProperty("mq.login.queue.name"), true);
//    }
//
//    @Bean
//    public TopicExchange loginExchange() {
//        return new TopicExchange(environment.getProperty("mq.login.exchange.name"));
//    }
//
//    @Bean
//    public Binding loginBinding() {
//        // 确定登录绑定关系
//        return BindingBuilder.bind(loginQueue()).to(loginExchange()).with(environment.getProperty("mq.login.routing.name"));
//    }
}
