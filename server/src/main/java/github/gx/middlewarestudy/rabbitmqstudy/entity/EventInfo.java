package github.gx.middlewarestudy.rabbitmqstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: MiddlewareStudy
 * @description: 实体对象信息 ——测试群发策略
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-24 17:22
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventInfo implements Serializable {

    private Integer id;
    private String module;
    private String name;
    private String desc;
}
