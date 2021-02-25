package github.gx.middlewarestudy.rabbitmqstudy.entity;

import github.gx.middlewarestudy.rabbitmqstudy.publisher.KnowledgePublisher;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: MiddlewareStudy
 * @description: 消息消费实体对象
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-25 11:56
 **/
@Data
@ToString
public class KnowledgeInfo implements Serializable {

    private Integer id;
    private String mode;
    private String code;

    public KnowledgeInfo(){}
}
