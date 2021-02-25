package github.gx.middlewarestudy.event;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * @program: MiddlewareStudy
 * @description: 消息实体
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-22 14:30
 **/
@Data
@ToString
public class LoginEvent extends ApplicationEvent implements Serializable {

    private String userName;
    private String loginTime;
    private String ip;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public LoginEvent(Object source) {
        super(source);
    }

    public LoginEvent(Object source, String userName, String loginTime, String ip) {
        super(source);
        this.userName = userName;
        this.loginTime = loginTime;
        this.ip = ip;
    }
}
