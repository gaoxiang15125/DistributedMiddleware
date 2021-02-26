package github.gx.middlewarestudy.server.redisimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import github.gx.mapper.LoginMapper;
import github.gx.mapper.UserMapper;
import github.gx.middlewarestudy.dto.UserLoginDto;
import github.gx.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: MiddlewareStudy
 * @description: 用户相关服务
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-25 17:37
 **/
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    LoginMapper userMapper;

    public UserService(LoginMapper loginMapper) {
        this.userMapper = loginMapper;
    }

    /**
     * 实现用户登录相关逻辑
     */
    public Boolean login(UserLoginDto dto) throws Exception {
        // 等于说对用户登录结果的验证不是事实反馈的
        // 而是使用消息队列进行了一步解耦操作
        // 这么做的话 返回结果的时候不就要使用 http 了嘛
        User user = findUserByPasswordAndName(dto.getUserName(), dto.getPassword());
        if(user != null) {
            // 表示用户登录成功， 此时应该返回用户id
            return true;
        }
        return false;
    }

    /**
     * 根据 账号 密码 对数据进行检索操作
     */
    public User findUserByPasswordAndName(String userName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserName, userName)
                .eq(User::getPassword, password);
        return userMapper.selectOne(queryWrapper);
    }
}
