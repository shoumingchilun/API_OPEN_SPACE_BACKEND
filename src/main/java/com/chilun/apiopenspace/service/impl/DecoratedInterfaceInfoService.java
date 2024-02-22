package com.chilun.apiopenspace.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.mapper.InterfaceInfoMapper;
import com.chilun.apiopenspace.model.Masked.InterfaceInfoMasked;
import com.chilun.apiopenspace.model.dto.DeleteRequest;
import com.chilun.apiopenspace.model.dto.Route.InitRouteRequest;
import com.chilun.apiopenspace.model.dto.Route.SaveOrUpdateRouteRequest;
import com.chilun.apiopenspace.model.entity.InterfaceInfo;
import com.chilun.apiopenspace.service.InterfaceInfoService;
import com.chilun.apiopenspace.service.RouteService;
import com.chilun.apiopenspace.service.feign.FeignRouteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 齿轮
 * @date 2024-02-14-14:22
 */
@Service("InterfaceInfo&RouteService")
public class DecoratedInterfaceInfoService extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService, RouteService {

    @Resource(name = "InterfaceInfoService")
    InterfaceInfoService baseService;

    @Resource
    FeignRouteService routeService;

    @Override
    public long InterfaceRegister(Long userid, String requestPath, Integer requestMethod) {
        long l = baseService.InterfaceRegister(userid, requestPath, requestMethod);
        this.saveOrUpdateRoute(l, requestPath);
        return l;
    }

    @Override
    public boolean updateById(InterfaceInfo entity) {
        boolean update = baseService.updateById(entity);
        if (entity.getRequestPath() != null) {
            this.saveOrUpdateRoute(entity.getId(), entity.getRequestPath());
        }
        return update;
    }

    @Override
    public InterfaceInfo statusManage(Long id, Integer status) {
        return baseService.statusManage(id, status);
    }

    @Override
    public void InterfaceAbolish(Long id) {
        baseService.InterfaceAbolish(id);
        this.deleteRoute(id);
    }

    @Override
    public InterfaceInfoMasked getInterfaceInfoMasked(InterfaceInfo interfaceInfo) {
        return baseService.getInterfaceInfoMasked(interfaceInfo);
    }

    @Override
    public List<InterfaceInfoMasked> getInterfaceInfoMasked(List<InterfaceInfo> interfaceInfoList) {
        return baseService.getInterfaceInfoMasked(interfaceInfoList);
    }

    @Override
    public void refreshRoute() {
        boolean refresh = routeService.refresh();
        ThrowUtils.throwIf(!refresh, ErrorCode.SYSTEM_ERROR, "刷新路由缓存失败，请重试");
    }

    @Override
    public void saveOrUpdateRoute(Long id, String uri) {
        boolean add = routeService.add(new SaveOrUpdateRouteRequest(String.valueOf(id), uri));
        ThrowUtils.throwIf(!add, ErrorCode.SYSTEM_ERROR, "路由注册失败");
    }

    @Override
    public void deleteRoute(Long id) {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setId(id);
        boolean delete = routeService.delete(deleteRequest);
        ThrowUtils.throwIf(!delete, ErrorCode.SYSTEM_ERROR, "路由注销失败");
    }

    @Override
    public void initRoute() {
        List<InterfaceInfo> interfaceInfos = baseService.getBaseMapper().selectList(new QueryWrapper<>());
        InitRouteRequest initRouteRequest = new InitRouteRequest();
        List<SaveOrUpdateRouteRequest> list = new ArrayList<>();
        interfaceInfos.forEach(interfaceInfo -> {
            SaveOrUpdateRouteRequest request = new SaveOrUpdateRouteRequest(String.valueOf(interfaceInfo.getId()), interfaceInfo.getRequestPath());
            list.add(request);
        });
        initRouteRequest.setList(list);
        boolean init = routeService.init(initRouteRequest);
        ThrowUtils.throwIf(!init, ErrorCode.SYSTEM_ERROR, "路由初始化失败");
    }
}
