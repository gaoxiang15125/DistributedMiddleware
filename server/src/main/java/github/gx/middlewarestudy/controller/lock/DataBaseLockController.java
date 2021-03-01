package github.gx.middlewarestudy.controller.lock;

import github.gx.middlewarestudy.controller.lock.dto.UserAccountDto;
import github.gx.middlewarestudy.server.lock.DataBaseLockService;
import github.gx.middlewarestudy.util.defineresult.BaseResponse;
import github.gx.middlewarestudy.util.selfenum.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: MiddlewareStudy
 * @description: 基于数据库的乐观悲观锁
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-28 20:54
 **/
@RestController
@RequestMapping("/db")
public class DataBaseLockController {
    private static final Logger log = LoggerFactory.getLogger(DataBaseLockController.class);

    private DataBaseLockService dataBaseLockService;

    @Autowired
    public DataBaseLockController(DataBaseLockService dataBaseLockService) {
        this.dataBaseLockService = dataBaseLockService;
    }

    @RequestMapping(value = "/spilt/money")
    public BaseResponse getMoneyFromDB(@Validated @RequestBody UserAccountDto userAccountDto, BindingResult result) {
        if(result.hasErrors()) {
            return new BaseResponse(StatusEnum.InvalidParams);
        }
        // 使用乐观锁完成对应逻辑
        BaseResponse response = new BaseResponse(StatusEnum.Success);
        try{
            dataBaseLockService.takeMoney(userAccountDto);
            log.info("取款操作执行完成，相关信息为：{}", userAccountDto);
        }catch (Exception e){
            log.error("从账户里执行扣款操作过程出现错误", e);
            response = new BaseResponse(StatusEnum.Fail, e.getMessage());
        }
        return response;
    }
}
