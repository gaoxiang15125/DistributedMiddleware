package github.gx.middlewarestudy;

import github.gx.mapper.UserMapper;
import github.gx.model.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: MiddlewareStudy
 * @description:
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 15:11
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MainApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserReader() {
        System.out.println("这个错误是因为什么？？");
        List<UserEntity> userList = userMapper.selectList(null);
        System.out.println(userList.get(0));
    }



    @Test
    public void testListUsage() {
        List<Integer> infoList = new ArrayList<>();
        infoList.remove(infoList.size() - 1);
    }
}