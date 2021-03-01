package github.gx.middlewarestudy.server.lock;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import github.gx.mapper.lock.UserAccountMapper;
import github.gx.mapper.lock.UserAccountRecordMapper;
import github.gx.middlewarestudy.controller.lock.dto.UserAccountDto;
import github.gx.model.lock.UserAccount;
import github.gx.model.lock.UserAccountRecord;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: MiddlewareStudy
 * @description: 基于数据库级别的乐观、悲观锁服务
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-28 21:18
 **/
@Service
public class DataBaseLockService {
    // 定义日志对象
    private static final Logger log = LoggerFactory.getLogger(DataBaseLockService.class);

    private UserAccountMapper userAccountMapper;
    private UserAccountRecordMapper userAccountRecordMapper;

    @Autowired
    public DataBaseLockService(UserAccountMapper userAccountMapper, UserAccountRecordMapper userAccountRecordMapper) {
        this.userAccountMapper = userAccountMapper;
        this.userAccountRecordMapper = userAccountRecordMapper;
    }

    /**
     * 用户账户提取金额相关处理操作
     */
    public void takeMoney(UserAccountDto dto) throws Exception {
        //查询用户账户实体记录
        UserAccount userAccount = queryUserAccountByUserId(dto.getUserId());
        //判断实体记录是否存在，以及账户余额是否足够被提现
        if (userAccount!=null && userAccount.getAmount().subtract(dto.getAmount()).doubleValue()>0){
            // 使用乐观锁进行更新操作
            // 因为乐观锁直接对内容进行了更新 这里将 amount 修改为我们想要的结果

            userAccount.setAmount(userAccount.getAmount().subtract(dto.getAmount()));
            int result = updateWithHappyLock(dto, userAccount);
            if(result>0) {
                //同时记录提现成功时的记录
                UserAccountRecord record=new UserAccountRecord();
                //设置提现成功时的时间
                record.setCreateTime(new Date());
                //设置账户记录主键id
                record.setAccountId(userAccount.getId());
                //设置成功申请提现时的金额
                record.setMoney(dto.getAmount());
                //插入申请提现金额历史记录
                userAccountRecordMapper.insert(record);
                //打印日志
                log.info("当前待提现的金额为：{} 用户账户余额为：{}",dto.getAmount(), userAccount.getAmount());
            }
        }else {
            throw new Exception("账户不存在或账户余额不足!");
        }
    }
    // 无论是 乐观锁 还是 悲观锁 都是再数据库层面执行的操作本质上都是对 sql 的利用
    private UserAccount queryUserAccountByUserId(Integer userId) {
        QueryWrapper<UserAccount> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.lambda().eq(UserAccount::getUserId, userId);
        return userAccountMapper.selectOne(accountQueryWrapper);
    }

    /**
     * 使用 mybatis-plus 注解实现乐观锁
     * @return
     */
    private int updateWithHappyLock(UserAccountDto userAccountDto, UserAccount userAccount) {
        /**
         * 查阅资料后发现 使用 mybatis-plus 实现乐观锁 需要
         * 通过id查出该数据
         * 使用set方法进行修改
         * 使用 updateById 方法进行更新
         */
        // 传进来的数据已经修改了 剩余金额 使用 乐观锁对数据进行更新
        return userAccountMapper.updateById(userAccount);
//        UpdateWrapper<UserAccount> userAccountUpdateWrapper = new UpdateWrapper<>();
//        // 账号id 与 userId 相等
//        userAccountUpdateWrapper.lambda().ge(UserAccount::getUserId, userAccountDto.getUserId())
//                .ge(UserAccount::getAmount, userAccountDto.getAmount());
//        // mybatis plus 官网讲 使用 version wrapper 控件不能复用这个指什么？
//        // 意思是指这里只能更新 version 吗
//        return userAccountMapper.update(userAccount, userAccountUpdateWrapper);
    }

    /**
     * 悲观锁需要自己编写 sql 语句，比较麻烦 不再进行编写操作了，毕竟不是特别有意义
     */
    private int updateWithSadLock() {
        // 具体为什么自己编写 xml 而不是使用插件？ 因为自己编写 可以做更多的定制操作，语句结构、锁操作否很方便的实现
        return 1;
    }
}
