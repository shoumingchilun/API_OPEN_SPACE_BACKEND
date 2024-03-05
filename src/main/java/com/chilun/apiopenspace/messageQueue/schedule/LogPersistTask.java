package com.chilun.apiopenspace.messageQueue.schedule;

import com.chilun.apiopenspace.model.dto.message.AccessLogDTO;
import com.chilun.apiopenspace.model.dto.message.ErrorLogDTO;
import com.chilun.apiopenspace.model.entity.ExceptionRecord;
import com.chilun.apiopenspace.service.ExceptionRecordService;
import com.chilun.apiopenspace.service.InterfaceAccessService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.consumer.SimpleConsumer;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 齿轮
 * @date 2024-03-05-17:59
 */
@Slf4j
@Component
@EnableScheduling
public class LogPersistTask {

    @Resource
    ExceptionRecordService exceptionRecordService;

    @Resource
    InterfaceAccessService interfaceAccessService;

    @Resource(name = "errorConsumer")
    public SimpleConsumer errorConsumer;


    @Resource(name = "commonConsumer")
    public SimpleConsumer commonConsumer;

    @Scheduled(cron = "0 */1 * * * ?") // 每隔1min触发一次
    public void persistErrorLog() {
        log.info("持久化错误日志开始----------------");
        ObjectMapper objectMapper = new ObjectMapper();
        List<ExceptionRecord> exceptionRecordList = new ArrayList<>();
        List<MessageView> receives = new ArrayList<>();
        try {
            receives = errorConsumer.receive(10000, Duration.ofSeconds(60));
            receives.forEach(messageView -> {
                ByteBuffer body = messageView.getBody();
                byte[] bytes = new byte[body.remaining()];
                body.get(bytes);
                String messageContent = new String(bytes, StandardCharsets.UTF_8);
                try {
                    ErrorLogDTO errorLogDTO = objectMapper.readValue(messageContent, ErrorLogDTO.class);
                    exceptionRecordList.add(exceptionRecordService.getExceptionRecordByErrorLogDTO(errorLogDTO));
                } catch (JsonProcessingException e) {
                    log.error("消息解码失败：", e);
                }

            });
        } catch (ClientException e) {
            log.error("消费者接受消息失败", e);
        }
        receives.forEach(messageView -> {
            try {
                errorConsumer.ack(messageView);
            } catch (ClientException e) {
                ByteBuffer body = messageView.getBody();
                byte[] bytes = new byte[body.remaining()];
                body.get(bytes);
                String messageContent = new String(bytes, StandardCharsets.UTF_8);
                log.error("记录消息" + messageView.getMessageId() + "消费完成失败：" + messageContent, e);
            }
        });
        exceptionRecordService.saveBatch(exceptionRecordList);
        log.info("持久化错误日志结束----------------");
    }

    @Scheduled(cron = "30 */1 * * * ?") //3min
    public void persistCommonLog() {
        log.info("持久化日常日志开始----------------");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, int[]> addItemMap = new HashMap<>();
        List<MessageView> receives = new ArrayList<>();
        try {
            receives = commonConsumer.receive(10000, Duration.ofSeconds(60));
            receives.forEach(messageView -> {
                ByteBuffer body = messageView.getBody();
                byte[] bytes = new byte[body.remaining()];
                body.get(bytes);
                String messageContent = new String(bytes, StandardCharsets.UTF_8);
                try {
                    AccessLogDTO accessLogDTO = objectMapper.readValue(messageContent, AccessLogDTO.class);
                    addItemMap.computeIfAbsent(accessLogDTO.getAccesskey(), k -> new int[]{0, 0});
                    int[] times = addItemMap.get(accessLogDTO.getAccesskey());
                    if (!accessLogDTO.isSuccess()){
                        times[1]++;
                    }
                    times[0]++;
                } catch (JsonProcessingException e) {
                    log.error("消息解码失败：", e);
                }
            });
        } catch (ClientException e) {
            log.error("消费者接受消息失败", e);
        }
        List<InterfaceAccessService.BatchAddItem>list = new ArrayList<>();
        addItemMap.forEach((k, v) -> {
            list.add(new InterfaceAccessService.BatchAddItem(v[0], v[1], k));
        });
        interfaceAccessService.batchAddCallTimes(list);
        receives.forEach(messageView -> {
            try {
                errorConsumer.ack(messageView);
            } catch (ClientException e) {
                ByteBuffer body = messageView.getBody();
                byte[] bytes = new byte[body.remaining()];
                body.get(bytes);
                String messageContent = new String(bytes, StandardCharsets.UTF_8);
                log.error("记录消息" + messageView.getMessageId() + "消费完成失败：" + messageContent, e);
            }
        });
        log.info("持久化日常日志结束----------------");
    }
}
