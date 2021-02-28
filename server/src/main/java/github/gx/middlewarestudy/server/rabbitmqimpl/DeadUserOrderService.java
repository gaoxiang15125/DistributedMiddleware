package github.gx.middlewarestudy.server.rabbitmqimpl;

import github.gx.mapper.MqOrderMapper;
import github.gx.mapper.UserOrderMapper;
import github.gx.middlewarestudy.dto.UserOrderDto;
import github.gx.middlewarestudy.rabbitmqstudy.publisher.DeadOrderPublisher;
import github.gx.model.MqOrder;
import github.gx.model.UserOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: MiddlewareStudy
 * @description: 包含订单超时功能的订单实现服务
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-27 15:20
 **/
@Service
public class DeadUserOrderService {
    private static final Logger log = LoggerFactory.getLogger(DeadUserOrderService.class);

    UserOrderMapper userOrderMapper;
    MqOrderMapper mqOrderMapper;
    DeadOrderPublisher deadOrderPublisher;

    @Autowired
    public DeadUserOrderService(UserOrderMapper userOrderMapper, MqOrderMapper mqOrderMapper, DeadOrderPublisher deadOrderPublisher) {
        this.userOrderMapper = userOrderMapper;
        this.mqOrderMapper = mqOrderMapper;
        this.deadOrderPublisher = deadOrderPublisher;
    }

    // TODO 编写死信队列手法请求实现方法
    /**
     * 对于新建订单 加入死信队列，到达时间后再弹出
     */
    public void pushUserOrder(UserOrderDto userOrderDto) throws Exception {
        UserOrder userOrder = new UserOrder();
        // BeanUtils 复制操作 ？？
//        BeanUtils.copyProperties(userOrderDto, userOrder);
        userOrder.setUserId(userOrderDto.getUserId());
        userOrder.setOrderNo(userOrderDto.getOrderNo());
        userOrder.setStatus(1);
        userOrder.setCreateTime(new Date());
        userOrderMapper.insert(userOrder);
        log.info("用户下单成功，订单信息写入数据库{}",userOrder);

        // 将订单信息写入 消息队列，等待执行进一步操作
        // redis 相比消息队列更为轻量级，一般不使用
        Integer orderId = userOrder.getId();
        deadOrderPublisher.sendMsg(orderId);
    }

    /**
     * 记录失效订单，并写入数据库
     */
    public void updateUserOrderRecord(UserOrder userOrder){
        if(userOrder == null) {
            return;
        }

        try{
            // 本质上是提供给死信队列调用更新数据库的方法
            userOrder.setIsActive(0);
            userOrder.setUpdateTime(new Date());
            // 根据 id 刷新数据库即可
            userOrderMapper.updateById(userOrder);

            // 构造订单超时执行记录
            MqOrder mqOrder = new MqOrder();
//            mqOrder.setId();
            mqOrder.setMemo("订单超时未支付");
            mqOrder.setOrderId(userOrder.getId());
            mqOrder.setBusinessTime(new Date());
            mqOrderMapper.insert(mqOrder);
        } catch (Exception e) {
            log.error("死信队列处理过期订单出现异常",e);
        }
    }

}
