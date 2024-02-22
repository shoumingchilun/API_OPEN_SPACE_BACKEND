package com.chilun.apiopenspace.service.feign;

/**
 * @author 齿轮
 * @date 2024-02-14-13:07
 */

import com.chilun.apiopenspace.model.dto.DeleteRequest;
import com.chilun.apiopenspace.model.dto.Route.InitRouteRequest;
import com.chilun.apiopenspace.model.dto.Route.SaveOrUpdateRouteRequest;
import com.chilun.apiopenspace.model.entity.RoutePOJO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "gateway")
@Component
public interface FeignRouteService {
    @PostMapping("/manage/update")
    boolean add(@RequestBody SaveOrUpdateRouteRequest request);

    @PostMapping("/manage/init")
    boolean init(@RequestBody InitRouteRequest request);

    @PostMapping("/manage/delete")
    boolean delete(@RequestBody DeleteRequest request);

    @GetMapping("/manage/getAll")
    List<RoutePOJO> getAll();

    @GetMapping("/manage/refresh")
    boolean refresh();
}
