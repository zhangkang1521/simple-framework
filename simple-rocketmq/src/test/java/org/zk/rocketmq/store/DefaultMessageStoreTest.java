package org.zk.rocketmq.store;

import org.junit.Test;
import org.zk.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class DefaultMessageStoreTest {


    private DefaultMessageStore defaultMessageStore;


    @Test
    public void putMessage() {
        defaultMessageStore = new DefaultMessageStore();
        Message message1 = new Message();
        message1.setTopic("test-topic");
        message1.setBody("123".getBytes(StandardCharsets.UTF_8));
        defaultMessageStore.putMessage(message1);

//        Message message2 = new Message();
//        message2.setTopic("test-topic");
//        message2.setBody("1234".getBytes(StandardCharsets.UTF_8));
//        defaultMessageStore.putMessage(message2);
    }

}