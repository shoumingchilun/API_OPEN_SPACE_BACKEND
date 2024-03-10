package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.RateLimitStrategy;
import generator.service.RateLimitStrategyService;
import generator.mapper.RateLimitStrategyMapper;
import org.springframework.stereotype.Service;

/**
* @author 齿轮
* @description 针对表【rate_limit_strategy(接口访问RPS限制表)】的数据库操作Service实现
* @createDate 2024-03-10 17:22:10
*/
@Service
public class RateLimitStrategyServiceImpl extends ServiceImpl<RateLimitStrategyMapper, RateLimitStrategy>
    implements RateLimitStrategyService {

}




