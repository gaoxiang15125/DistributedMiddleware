package github.gx.middlewarestudy.dto.bookinfo;

import lombok.Data;
import lombok.ToString;

/**
 * @program: MiddlewareStudy
 * @description: 书籍抢购实体类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-02 13:08
 **/
@Data
@ToString
public class BookRobDto {
    Integer userId;
    String bookNo;
}
