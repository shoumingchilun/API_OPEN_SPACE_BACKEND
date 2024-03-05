package com.chilun.apiopenspace.queue.consumer;

import com.alibaba.fastjson.JSON;
import com.chilun.apiopenspace.model.dto.message.ErrorLogDTO;
import com.chilun.apiopenspace.service.ExceptionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息队列的消费者，用于接收异常日志消息并存储MessageStore
 *
 * @author 齿轮
 * @date 2024-03-01-15:12
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "API_SPACE", selectorExpression = "errorlog", consumerGroup = "API_BACKEND_2")
public class ErrorLogMessageConsumer implements RocketMQListener<ErrorLogDTO> {

    @Resource
    ExceptionRecordService exceptionRecordService;

    @Override
    public void onMessage(ErrorLogDTO message) {
        log.info("Save ErrorLog Message: " + JSON.toJSONString(message));
        exceptionRecordService.recordException(message.getAccesskey(), message.getUserid(), message.getInterfaceId(), message.getErrorReason(), message.getResponse(), message.getRequest());
    }
}
