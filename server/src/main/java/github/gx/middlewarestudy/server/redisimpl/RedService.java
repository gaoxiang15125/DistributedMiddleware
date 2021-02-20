package github.gx.middlewarestudy.server.redisimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import github.gx.middlewarestudy.dto.RedPacketDto;
import github.gx.middlewarestudy.model.RedDetail;
import github.gx.middlewarestudy.model.RedRecord;
import github.gx.middlewarestudy.server.IRedService;
import github.gx.middlewarestudy.server.mapper.RedDetailMapper;
import github.gx.middlewarestudy.server.mapper.RedRecordMapper;
import github.gx.middlewarestudy.server.mapper.RedRobRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: MiddlewareStudy
 * @description: 红包业务逻辑处理过程数据记录接口 接口实现类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-20 14:02
 **/
@Service
@EnableAsync
// 通过注解实现方法的异步调用
public class RedService implements IRedService {
    // 日志操作类
    private static final Logger log = LoggerFactory.getLogger(RedService.class);

    // 数据操作 Mapper 三个对象组合 成为操作数据库的关键
    @Autowired
    private RedRecordMapper redRecordMapper;
    @Autowired
    private RedDetailMapper redDetailMapper;
    @Autowired
    private RedRobRecordMapper redRobRecordMapper;

    @Autowired
    public RedService(RedRecordMapper redRecordMapper, RedDetailMapper redDetailMapper, RedRobRecordMapper redRobRecordMapper) {
        this.redRecordMapper = redRecordMapper;
        this.redDetailMapper = redDetailMapper;
        this.redRobRecordMapper = redRobRecordMapper;
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception {
        // 定义实体类对象，并将相关信息写入数据库
        // 等于说红包发成功就写入数据库了，并没有额外的操作了
        // 构造实体类，写入数据库中
        RedRecord redRecord = new RedRecord();
        redRecord.setUserId(dto.getUserId());
        redRecord.setRedPacket(redId);
        redRecord.setTotal(dto.getTotal());
        redRecord.setAmount(BigDecimal.valueOf(dto.getAmount()));
        redRecord.setCreateTime(new Date());
        redRecordMapper.insert(redRecord);
        // 通过 Mapper 将对象写入数据库
        redRecord = getRedRecordByRedPacket(redRecord.getRedPacket());
        // 定义红包分配结果对象，存入数据库
        RedDetail detail;
        for(Integer i:list) {
            detail = new RedDetail();
            detail.setRecordId(redRecord.getId());
            detail.setAmount(BigDecimal.valueOf(i));
            detail.setCreateTime(new Date());
            // 将对象信息插入数据库
            redDetailMapper.insert(detail);
        }
    }

    @Override
    @Async
    public void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception {

    }

    /**
     * 根据 红包唯一识别码 检索目标红包
     * @param redPacket
     * @return
     */
    private RedRecord getRedRecordByRedPacket(String redPacket) {
        QueryWrapper<RedRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RedRecord::getRedPacket,redPacket);
        return redRecordMapper.selectOne(queryWrapper);
    }
}
