package github.gx.mapper.lock;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.gx.model.lock.UserAccount;
import org.springframework.stereotype.Repository;

/**
 * @program: MiddlewareStudy
 * @description: 用户账号相关数据库操作接口
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-28 20:44
 **/
@Repository
public interface UserAccountMapper extends BaseMapper<UserAccount> {
}
