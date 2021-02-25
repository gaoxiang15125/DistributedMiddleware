package github.gx.middlewarestudy.rabbitmqstudy.consumer;

import github.gx.middlewarestudy.rabbitmqstudy.entity.EventInfo;
import github.gx.middlewarestudy.rabbitmqstudy.publisher.ModelPublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @program: MiddlewareStudy
 * @description:
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-24 18:40
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ModelConsumerTest {

    @Autowired
    ModelPublisher modelPublisher;

    @Test
    public void test3() throws Exception {
        EventInfo info = new EventInfo(1,"想要说点什么嘛", "算了","就这样吧 ");
        modelPublisher.sendMsg(info);
    }
}