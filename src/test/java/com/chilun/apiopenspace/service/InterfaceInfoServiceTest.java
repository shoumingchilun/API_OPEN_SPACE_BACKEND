package com.chilun.apiopenspace.service;

import com.chilun.apiopenspace.constant.RequestMethodValue;
import com.chilun.apiopenspace.model.entity.InterfaceInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 齿轮
 * @date 2024-01-27-20:30
 */
@SpringBootTest
class InterfaceInfoServiceTest {
    @Resource(name="InterfaceInfoService")
    InterfaceInfoService interfaceInfoService;

    @Test
    void all() {
        long l = interfaceInfoService.InterfaceRegister(28L, "127.0.0.1:8080/test/hello", RequestMethodValue.GET);
        System.out.println(l);
        InterfaceInfo interfaceInfo = interfaceInfoService.statusManage(l, 1);
        System.out.println(interfaceInfoService.getInterfaceInfoMasked(interfaceInfo));
        interfaceInfoService.InterfaceAbolish(l);
    }
}