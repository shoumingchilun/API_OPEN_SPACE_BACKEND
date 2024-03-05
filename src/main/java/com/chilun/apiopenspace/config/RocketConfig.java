package com.chilun.apiopenspace.config;

import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.SimpleConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Collections;

/**
 * @author 齿轮
 * @date 2024-03-05-17:42
 */
@Configuration
public class RocketConfig {
    @Value("${message-queue-meta.proxy-server}")
    private  String endpoints;
    @Value("${message-queue-meta.topic}")
    private String topic;
    @Value("${message-queue-meta.common-log-tag}")
    private String commonTag;
    @Value("${message-queue-meta.error-log-tag}")
    private String errorTag;



    @Bean(name = "errorConsumer")
    public SimpleConsumer errorConsume() throws ClientException {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(endpoints)
                .enableSsl(false)
                .build();
        String consumerGroup = "API_BACKEND_ERROR";
        Duration awaitDuration = Duration.ofSeconds(10);
        FilterExpression filterExpression = new FilterExpression(errorTag, FilterExpressionType.TAG);
        return provider.newSimpleConsumerBuilder()
                .setClientConfiguration(clientConfiguration)
                .setConsumerGroup(consumerGroup)
                .setAwaitDuration(awaitDuration)
                .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                .build();
    }

    @Bean(name = "commonConsumer")
    public SimpleConsumer beginConsume() throws ClientException {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(endpoints)
                .enableSsl(false)
                .build();
        String consumerGroup = "API_BACKEND_COMMON";
        Duration awaitDuration = Duration.ofSeconds(30);
        FilterExpression filterExpression = new FilterExpression(commonTag, FilterExpressionType.TAG);
        return provider.newSimpleConsumerBuilder()
                .setClientConfiguration(clientConfiguration)
                .setConsumerGroup(consumerGroup)
                .setAwaitDuration(awaitDuration)
                .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                .build();
    }
}
