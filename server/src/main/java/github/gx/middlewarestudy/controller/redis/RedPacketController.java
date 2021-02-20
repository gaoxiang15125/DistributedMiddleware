package github.gx.middlewarestudy.controller.redis;

import github.gx.middlewarestudy.dto.RedPacketDto;
import github.gx.middlewarestudy.server.IRedPacketService;
import github.gx.middlewarestudy.util.defineresult.BaseResponse;
import github.gx.middlewarestudy.util.selfenum.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: MiddlewareStudy
 * @description: 红包处理 Controller
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-20 13:38
 **/
@RestController
@RequestMapping("/red/packet")
public class RedPacketController {
    // TODO 入参中多了一个 BindingResult 这个是做什么的？
    // 日志对象
    private static final Logger log = LoggerFactory.getLogger(RedPacketController.class);

    private IRedPacketService redPacketService;

    @Autowired
    public RedPacketController(IRedPacketService redPacketService) {
        this.redPacketService = redPacketService;
    }

    @RequestMapping(value = "/hand/out", method = RequestMethod.POST)
    public BaseResponse handOut(@Validated @RequestBody RedPacketDto dto, BindingResult result) {
        // 使用 Spring 提供的组件进行参数校验
        if(result.hasErrors()) {
            return new BaseResponse(StatusEnum.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusEnum.Success);
        try {
            // 将红包信息 存入 redis 数据库
            String redId = redPacketService.handOut(dto);
            response.setData(redId);
        } catch (Exception e) {
            // 打印异常日志，并返回相应错误信息
            log.error("构造红包过程出现错误: dto={}", dto, e);
        }
        return response;
    }

}
