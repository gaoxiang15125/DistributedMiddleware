package github.gx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.gx.model.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * @program: MiddlewareStudy
 * @description: 用户 Mapper，用来实现 增删改查
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 15:04
 **/
@Repository
// 这个注解会不会造成资源浪费 ？？
public interface UserMapper extends BaseMapper<UserEntity> {
}
