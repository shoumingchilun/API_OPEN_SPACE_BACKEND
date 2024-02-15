package com.chilun.apiopenspace.model.dto.Route;

import lombok.Data;

/**
 * @author 齿轮
 * @date 2024-02-13-19:55
 */
@Data
public class SaveOrUpdateRouteRequest {
    String id;
    String uri;

    public SaveOrUpdateRouteRequest() {
    }

    public SaveOrUpdateRouteRequest(String id, String uri) {
        this.id = id;
        this.uri = uri;
    }
}
