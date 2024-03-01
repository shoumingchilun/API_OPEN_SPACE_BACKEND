package com.chilun.apiopenspace.queue.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


/**
 * 用于临时存储接口的调用信息，并定时将其持久化到数据库
 * （暂未启用，准备后续优化：拼接SQL进行批量插入）
 *
 * @author 齿轮
 * @date 2024-03-01-17:57
 */
@Slf4j
@Component
@EnableScheduling
public class ErrorLogMessageStore {
//    private final Object lock = new Object();
//
//
//
//    public void tempStoreMessage(AccessLogDTO accessLogDTO) {
//        synchronized (lock) {
//
//        }
//    }
//
//    @Scheduled(cron = "0 */5 * * * ?") // 每隔5min触发一次
//    public void persistMessage() {
//        log.info("持久化ErrorLog开始------");
//        synchronized (lock) {
//
//        }
//        log.info("持久化ErrorLog结束------");
//    }
}
