package github.gx.middlewarestudy.util.defineresult;

import github.gx.middlewarestudy.util.selfenum.StatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * @program: MiddlewareStudy
 * @description: 默认返回的 Json 格式
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 21:46
 **/
@Data
public class BaseResponse<T> {

    /**
     * 自定义状态码
     */
    int statusCode;

    /**
     * 返回结果描述信息
     */
    String message;

    /**
     * 返回结果实体类
     */
    T data;

    /**
     * 返回时间
     */
    Date returnTime;

    public BaseResponse() {}

    public BaseResponse(StatusEnum statusEnum) {
        this.statusCode = statusEnum.getCode();
        this.message = statusEnum.getMessage();
        returnTime = new Date();
    }
}
