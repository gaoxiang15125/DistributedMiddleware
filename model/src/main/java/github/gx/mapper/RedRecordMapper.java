package github.gx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.gx.model.RedDetail;
import github.gx.model.RedRecord;
import org.springframework.stereotype.Repository;

/**
 * @program: MiddlewareStudy
 * @description: 红包分配后金额明细
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 21:07
 **/
@Repository
public interface RedRecordMapper extends BaseMapper<RedRecord> {
}
