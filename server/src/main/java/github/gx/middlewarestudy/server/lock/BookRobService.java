package github.gx.middlewarestudy.server.lock;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import github.gx.mapper.bookstore.BookRobMapper;
import github.gx.mapper.bookstore.BookStoreMapper;
import github.gx.middlewarestudy.dto.bookinfo.BookRobDto;
import github.gx.model.bookstore.BookStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: MiddlewareStudy
 * @description: 书籍抢购服务处理类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-02 12:59
 **/
@Service
public class BookRobService {
    // 日志操作对象
    private static final Logger log = LoggerFactory.getLogger(BookRobService.class);
    // 书籍数据 操作实体类
    BookStoreMapper bookStoreMapper;
    BookRobMapper bookRobMapper;

    @Autowired
    public BookRobService(BookStoreMapper bookStoreMapper, BookRobMapper bookRobMapper) {
        this.bookRobMapper = bookRobMapper;
        this.bookStoreMapper = bookStoreMapper;
    }

    public void robWithZKLock(BookRobDto dto) throws Exception {
        // 根据书籍编号查询记录
        BookStock stock = getBookStockByBookNo(dto);
        // 本质上流程与 抢红包相同，额外增加了 zookeeper 的加锁、去锁操作而已
    }

    private BookStock getBookStockByBookNo(BookRobDto dto) {
        QueryWrapper<BookStock> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BookStock::getBookNo, dto.getBookNo());
        return bookStoreMapper.selectOne(queryWrapper);
    }
}
