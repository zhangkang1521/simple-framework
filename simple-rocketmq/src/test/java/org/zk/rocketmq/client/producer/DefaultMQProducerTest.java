package org.zk.rocketmq.client.producer;

import lombok.SneakyThrows;
import org.junit.Test;
import org.zk.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class DefaultMQProducerTest {

    @Test
    @SneakyThrows
    public void send() {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.start();

        Message message = new Message();
        message.setTopic("test-topic");
        message.setBody("abc".getBytes(StandardCharsets.UTF_8));

        producer.send(message);

        System.in.read();
    }
}