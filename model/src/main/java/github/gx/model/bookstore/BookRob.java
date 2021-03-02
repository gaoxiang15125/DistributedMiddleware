package github.gx.model.bookstore;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @program: MiddlewareStudy
 * @description: 书籍抢购记录实体
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-01 20:35
 **/
@Data
@ToString
public class BookRob {
    // 主键 id
    private Integer id;
    // 用户 id
    private Integer userId;
    // 书籍编号
    private String bookNo;
    // 抢购时间
    private Date robTime;
}
