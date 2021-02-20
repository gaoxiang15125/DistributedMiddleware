package github.gx.middlewarestudy.util.selfenum;

/**
 * @program: MiddlewareStudy
 * @description: 与系统框架相关的枚举量
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 21:48
 **/
public enum StatusEnum {

    Success(200, "请求成功"),
    Fail(-1, "请求错误"),
    InvalidParams(201, "非法参数"),
    InvalidGrantType(202, "非法授权类型");

    String message;
    Integer code;

    StatusEnum(int code ,String message) {
        this.code = code;
        this.message = message;
    }

    // 只需要对外提供 get 方法
    public String getMessage() {
        return this.message;
    }

    public Integer getCode() {
        return code;
    }
}
