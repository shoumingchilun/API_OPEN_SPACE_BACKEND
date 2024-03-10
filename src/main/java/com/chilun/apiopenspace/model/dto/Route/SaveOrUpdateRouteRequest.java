package com.chilun.apiopenspace.model.dto.Route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 齿轮
 * @date 2024-02-13-19:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveOrUpdateRouteRequest {
    String id;
    String uri;

    Integer replenishRate;
    Integer burstCapacity;
    Integer requestedTokens;

    public SaveOrUpdateRouteRequest(String id, String uri) {
        this.id = id;
        this.uri = uri;
    }
}
