package github.gx.middlewarestudy.controller;

import github.gx.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: MiddlewareStudy
 * @description: 你好控制器
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 15:56
 **/
@RestController
@RequestMapping("/test")
public class HelloWorldController {
    private static final Logger log = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public UserEntity creatUser(String userName, String age, String telPhone) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userName);
        userEntity.setAge(age);
        userEntity.setTelPhone(telPhone);
        return userEntity;
    }

}
