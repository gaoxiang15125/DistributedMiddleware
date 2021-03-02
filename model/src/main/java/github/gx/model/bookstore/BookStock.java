package github.gx.model.bookstore;

import lombok.Data;
import lombok.ToString;

/**
 * @program: MiddlewareStudy
 * @description: 书籍商店
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-01 20:34
 **/
@Data
@ToString
public class BookStock {
    // 主键id
    private Integer id;
    // 书籍编号
    private String bookNo;
    // 库存
    private Integer stock;
    // 是否上架
    private Byte isActive;
}
