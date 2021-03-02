package github.gx.mapper.bookstore;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.gx.model.bookstore.BookStock;
import org.springframework.stereotype.Repository;

/**
 * @program: MiddlewareStudy
 * @description: 书城数据库操作接口类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-01 20:37
 **/
@Repository
public interface BookStoreMapper extends BaseMapper<BookStock> {
}
