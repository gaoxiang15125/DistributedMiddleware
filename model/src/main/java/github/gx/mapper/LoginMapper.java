package github.gx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.gx.model.User;
import org.springframework.stereotype.Repository;

/**
 * @program: MiddlewareStudy
 * @description: 登录相关用户接口
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-25 17:32
 **/
@Repository
public interface LoginMapper extends BaseMapper<User> {
}
