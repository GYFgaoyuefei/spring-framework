package org.ali.rockermq.controller;

import javax.websocket.server.PathParam;

import org.ali.rockermq.producer.RmProducerRunner;
import org.ali.rockermq.producer.RmqProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rocketmq/test")
public class TestController {

    @Autowired
    RmqProducer rocketmqProducer;
    
    @Autowired
    RmProducerRunner rmProducerRunner;

    @RequestMapping(value = "/send/{message}", method = RequestMethod.GET)
    public String send(@PathVariable(value = "message") String message) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        rocketmqProducer.send(message);
        return "success";
    }
    
    @RequestMapping(value = "/sendbyRun/{message}", method = RequestMethod.GET)
    public String sendbyRun(@PathVariable(value = "message") String message) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
    	try {
			rmProducerRunner.run(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "success";
    }
}
