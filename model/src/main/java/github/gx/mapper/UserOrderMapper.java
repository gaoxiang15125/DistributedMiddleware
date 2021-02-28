package github.gx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.gx.model.UserOrder;
import org.springframework.stereotype.Repository;

/**
 * @program: MiddlewareStudy
 * @description: 用户订单操作接口
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-26 14:44
 **/
@Repository
public interface UserOrderMapper extends BaseMapper<UserOrder> {
}
