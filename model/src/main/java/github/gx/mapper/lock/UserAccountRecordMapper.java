package github.gx.mapper.lock;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.gx.model.lock.UserAccountRecord;
import org.springframework.stereotype.Repository;

/**
 * @program: MiddlewareStudy
 * @description: UserAccountRecord 实体类对应 Mapper 操作窗口
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-28 20:48
 **/
@Repository
public interface UserAccountRecordMapper extends BaseMapper<UserAccountRecord> {
}
