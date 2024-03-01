package com.chilun.apiopenspace.queue.consumer;

import com.chilun.apiopenspace.model.dto.message.AccessLogDTO;
import com.chilun.apiopenspace.queue.store.CommonLogMessageStore;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息队列的消费者，用于接收普通日志消息并存储MessageStore
 *
 * @author 齿轮
 * @date 2024-03-01-15:12
 */
@Component
@RocketMQMessageListener(topic = "API_SPACE", selectorExpression = "commonlog", consumerGroup = "API_BACKEND_1")
public class CommonLogMessageConsumer implements RocketMQListener<AccessLogDTO> {

    @Resource
    CommonLogMessageStore commonLogMessageStore;

    @Override
    public void onMessage(AccessLogDTO message) {
        commonLogMessageStore.tempStoreMessage(message);
    }
}
