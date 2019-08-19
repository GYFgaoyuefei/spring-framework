package com.eseasky.core.framework.Logger.consumers;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.Logger.core.LogSaveAction;
import com.eseasky.core.framework.Logger.exception.LogSaveFailedException;
import com.eseasky.core.starters.rabbitmq.entity.Message;
import com.eseasky.global.entity.HttpMessage;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class LogRecordConsumer {
	@Autowired
	LogSaveAction logSaveAction;
	
	@RabbitListener(queues = "${logger.persistence.queueName:logger.persistence}", concurrency="${logger.persistence.concurrency:5}")
	public void logRecord(Message<HttpMessage> message) {
		List<HttpMessage> msgs = message.getMessages();
		for (HttpMessage msg: msgs) {
			try {
				logSaveAction.saveLog(msg);
			} catch (LogSaveFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		
	}
}
