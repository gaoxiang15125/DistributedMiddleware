package github.gx.middlewarestudy.controller;

import github.gx.middlewarestudy.dto.UserOrderDto;
import github.gx.middlewarestudy.server.rabbitmqimpl.DeadUserOrderService;
import github.gx.middlewarestudy.util.defineresult.BaseResponse;
import github.gx.middlewarestudy.util.selfenum.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: MiddlewareStudy
 * @description: 用户订单相关控制器
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-27 15:11
 **/
@RestController
@RequestMapping("/user/order")
public class UserOrderController {
    // 定义日志
    private static final Logger log = LoggerFactory.getLogger(UserOrderController.class);

    DeadUserOrderService deadUserOrderService;

    @Autowired
    public UserOrderController(DeadUserOrderService deadUserOrderService) {
        this.deadUserOrderService = deadUserOrderService;
    }

    @RequestMapping(value = "/creat", method = RequestMethod.POST)
    // 对于 get 请求怎么将 json 作为参数 ？？
    public BaseResponse creatOrder(@RequestBody @Validated UserOrderDto userOrderDto) {
        BaseResponse response;
        if(userOrderDto == null) {
            response = new BaseResponse(StatusEnum.InvalidParams);
            return response;
        }
        try{
            deadUserOrderService.pushUserOrder(userOrderDto);
            response = new BaseResponse(StatusEnum.Success);
        } catch (Exception e) {
            log.error("创建订单过程中出现错误", e);
            response = new BaseResponse(StatusEnum.Fail, e.getMessage());
        }
        return response;
    }
}
