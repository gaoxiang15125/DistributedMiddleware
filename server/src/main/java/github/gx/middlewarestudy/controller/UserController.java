package github.gx.middlewarestudy.controller;

import github.gx.middlewarestudy.dto.UserLoginDto;
import github.gx.middlewarestudy.server.redisimpl.UserService;
import github.gx.middlewarestudy.util.defineresult.BaseResponse;
import github.gx.middlewarestudy.util.selfenum.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: MiddlewareStudy
 * @description: 用户相关控制器
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-25 17:53
 **/
@RestController
public class UserController {

    static final Logger log = LoggerFactory.getLogger(UserController.class);

    //前端请求前缀
    private static final String prefix="user";
    //注入用户操作Service层实例
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @return
     */
    @RequestMapping(value = prefix+"/login",method = RequestMethod.POST)
    public BaseResponse login(@RequestBody @Validated UserLoginDto dto,
                              BindingResult result){
        //校验前端用户提交的用户登录信息的合法性
        if (result.hasErrors()){
            return new BaseResponse(StatusEnum.InvalidParams);
        }
        //定义返回结果实例
        BaseResponse response=new BaseResponse(StatusEnum.Success);
        try {
            //调用Service层方法真正处理用户登录逻辑
            Boolean res=userService.login(dto);
            // 使用消息队列异步记录用户登录状态

            if (res){
                //表示res=true，即用户登录成功
                response=new BaseResponse(StatusEnum.Success,"登录成功");
            }else{
                //表示res=false，即用户登录失败
                response=new BaseResponse(StatusEnum.Fail,"登录失 败-账户名密码不匹配");
            }
        }catch (Exception e){
            //表示处理过程发生异常
            response=new BaseResponse(StatusEnum.Fail,e.getMessage());
        }
        //返回最终处理的结果
        return response;
    }
}
