package com.chilun.apiopenspace.model.dto.Route;

import lombok.Data;

import java.util.List;

/**
 * @author 齿轮
 * @date 2024-02-13-20:00
 */
@Data
public class InitRouteRequest {
    List<SaveOrUpdateRouteRequest> list;
}