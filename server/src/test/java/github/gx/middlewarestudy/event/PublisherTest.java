package github.gx.middlewarestudy.event;

import github.gx.middlewarestudy.MainApplication;
import github.gx.middlewarestudy.rabbitmqstudy.entity.Person;
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
 * @create: 2021-02-22 14:59
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PublisherTest {

    @Autowired
    Publisher publisher;

    @Test
    public void sendMsg() {
        try {
            publisher.sendMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}