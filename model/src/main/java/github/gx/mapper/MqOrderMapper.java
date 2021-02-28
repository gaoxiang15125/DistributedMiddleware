package github.gx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.gx.model.MqOrder;
import org.springframework.stereotype.Repository;

/**
 * @program: MiddlewareStudy
 * @description: 失效订单操作实现接口
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-26 14:42
 **/
@Repository
public interface MqOrderMapper extends BaseMapper<MqOrder> {
}
