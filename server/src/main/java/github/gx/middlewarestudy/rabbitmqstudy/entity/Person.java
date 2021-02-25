package github.gx.middlewarestudy.rabbitmqstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: MiddlewareStudy
 * @description: 使用消息队列传输对象信息
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-23 21:39
 **/
@Data
@ToString
public class Person implements Serializable {

    private Integer id;
    private String name;
    private String userName;

    public Person() {}

}
