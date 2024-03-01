package com.chilun.apiopenspace.queue.store;

import com.chilun.apiopenspace.model.dto.message.AccessLogDTO;
import com.chilun.apiopenspace.service.InterfaceAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于临时存储接口的调用信息，并定时将其持久化到数据库
 *
 * @author 齿轮
 * @date 2024-03-01-17:57
 */
@Slf4j
@Component
@EnableScheduling
public class CommonLogMessageStore {
    private final Object lock = new Object();
    public ConcurrentHashMap<String, int[]> accesskeyCallTimes = new ConcurrentHashMap<>();

    @Resource
    InterfaceAccessService interfaceAccessService;

    public void tempStoreMessage(AccessLogDTO accessLogDTO) {
        synchronized (lock) {
            String accesskey = accessLogDTO.getAccesskey();
            accesskeyCallTimes.computeIfAbsent(accesskey, k -> new int[]{0, 0});
            accesskeyCallTimes.get(accesskey)[0]++;
            if (!accessLogDTO.isSuccess()) {
                accesskeyCallTimes.get(accesskey)[1]++;
            }
        }
    }

    @Scheduled(cron = "0 */5 * * * ?") // 每隔5min触发一次
    public void persistMessage() {
        log.info("持久化CommonLog开始------");
        synchronized (lock) {
            ConcurrentHashMap<String, int[]> oldCallTimes = accesskeyCallTimes;
            accesskeyCallTimes = new ConcurrentHashMap<>();
            oldCallTimes.forEach((k, v) -> {
                interfaceAccessService.addCallTimes(v[0], v[1], k);
            });
            oldCallTimes.clear();
        }
        log.info("持久化CommonLog结束------");
    }
}
