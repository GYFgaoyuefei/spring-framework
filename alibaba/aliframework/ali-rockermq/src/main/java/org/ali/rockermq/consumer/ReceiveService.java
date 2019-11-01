package org.ali.rockermq.consumer;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class ReceiveService {

    /**
     * 默认是input，在Sink类中指定，如果想要多个input，需要写一个实现Sink的类
     * @param receiveMsg
     */
    @StreamListener("input1")
    public void receiveInput1(String receiveMsg) {
        System.out.println("input1 receive: " + receiveMsg);
    }
    
    @StreamListener("input2")
    public void receiveInput2(String receiveMsg) {
        System.out.println("input2 receive: " + receiveMsg);
    }

}
