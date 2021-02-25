package github.gx.model;

import java.util.Date;

/**
 * @program: MiddlewareStudy
 * @description: 系统日志记录实体类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-25 15:11
 **/
public class SysLog {
    Integer id;
    Integer userId;
    String module;
    String data;
    String memo;
    Date createTime;
}
