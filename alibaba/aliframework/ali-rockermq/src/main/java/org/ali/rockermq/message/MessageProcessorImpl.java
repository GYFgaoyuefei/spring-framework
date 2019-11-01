package org.ali.rockermq.message;

import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessorImpl implements MessageProcessor {
    @Override
    public boolean handle(MessageExt messageExt) {
    //这里收到的body（也就是消息体）是字节类型，转为String
        String result = new String(messageExt.getBody());
        System.out.println("收到了消息："+ result);
        return true;
    }
}
