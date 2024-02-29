package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.ExceptionRecord;
import generator.service.ExceptionRecordService;
import generator.mapper.ExceptionRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author 齿轮
* @description 针对表【exception_record(异常记录表)】的数据库操作Service实现
* @createDate 2024-02-27 15:17:22
*/
@Service
public class ExceptionRecordServiceImpl extends ServiceImpl<ExceptionRecordMapper, ExceptionRecord>
    implements ExceptionRecordService{

}




