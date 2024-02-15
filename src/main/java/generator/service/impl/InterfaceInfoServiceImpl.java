package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.InterfaceInfo;
import generator.service.InterfaceInfoService;
import generator.mapper.InterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 齿轮
* @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
* @createDate 2024-01-27 15:08:08
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

}




