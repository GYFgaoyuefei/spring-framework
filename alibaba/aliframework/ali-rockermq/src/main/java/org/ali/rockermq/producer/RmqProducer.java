package org.ali.rockermq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
@RefreshScope
public class RmqProducer {
	@Value("${spring.cloud.stream.rocketmq.binder.name-server}")
	private String namesrvAddr;

	@Value("${spring.cloud.stream.bindings.output1.group}")
	private String producerGroup;
	
	@Autowired
	private Mysource mysource;

	public void send(String message)
			throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
//		DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
//		producer.setNamesrvAddr(namesrvAddr);
//		producer.start();
//		Message msg = new Message("test-topic", "test-tag", message.getBytes());
//		producer.send(msg);
		User user=new User();
		user.setName(message);
		Message objMes = MessageBuilder.withPayload(user)
				.setHeader(MessageConst.PROPERTY_TAGS, "obj")
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build();
		mysource.output1().send(objMes);
	}
}
