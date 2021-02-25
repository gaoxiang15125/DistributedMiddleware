package github.gx.middlewarestudy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.gx.middlewarestudy.rabbitmqstudy.entity.KnowledgeInfo;
import github.gx.middlewarestudy.rabbitmqstudy.entity.Person;
import github.gx.middlewarestudy.rabbitmqstudy.publisher.BasicPublisher;
import github.gx.middlewarestudy.rabbitmqstudy.publisher.KnowledgePublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @program: MiddlewareStudy
 * @description:
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-23 14:40
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RabbitmqConfigTest {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfigTest.class);

    @Autowired
    ObjectMapper objectMapper;
    // 定义基本消息模型中发送消息的生产者
    @Autowired
    BasicPublisher basicPublisher;

    @Test
    public void testOne() throws Exception {
        String message = "测试用字符串信息";
        basicPublisher.sendMsg(message);
    }

    @Test
    public void testObjectInfo() {
        Person person = new Person();
        person.setId(1);
        person.setName("ojbk");
        person.setUserName("哪有长生无敌");
        basicPublisher.sendObjectMsg(person);
    }

    @Autowired
    KnowledgePublisher knowledgePublisher;

    @Test
    public void testAutoCallBack() {
        KnowledgeInfo info = new KnowledgeInfo();
        info.setId(10010);
        info.setCode("auto");
        info.setMode("基于Auto 的消息确认消费模式");
        knowledgePublisher.sendAutoMsg(info);
    }
}