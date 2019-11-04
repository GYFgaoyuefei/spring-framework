package org.ali.rockermq.producer;

import java.util.HashMap;
import java.util.Map;


import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RmProducerRunner implements CommandLineRunner {
    @Autowired
    private Mysource mysource; // 获取name为output的binding
    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "tagStr");
        Message strMes= MessageBuilder.createMessage(args, new MessageHeaders(headers));
//        Message objMes = MessageBuilder.withPayload(new User("aa"))
//				.setHeader(MessageConst.PROPERTY_TAGS, "obj")
//				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
//				.build();
        mysource.output2().send(strMes);
//        mysource.output2().send(objMes);
    }

}
