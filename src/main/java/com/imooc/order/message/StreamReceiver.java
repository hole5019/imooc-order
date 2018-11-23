package com.imooc.order.message;

import com.imooc.order.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Created by helei on 2018-11-21.
 */
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

//    @StreamListener("myMessageInput")
//    public void process(String message){
//        log.info("StreamReceiver: {}",message);
//    }

    /**
     * 接收Orderdto对象
     * @param message
     */
//    @StreamListener(StreamClient.INPUT)
//    public void process(OrderDto message){
//        log.info("StreamReceiver: {}",message);
//    }

    /**
     * 接收Orderdto对象并回应消息
     * @param message
     */
    @StreamListener(StreamClient.INPUT)
    @SendTo(StreamClient.INPUT2)
    public String process(OrderDto message){
        log.info("StreamReceiver: {}",message);
        return "received==";
    }

    @StreamListener(StreamClient.INPUT2)
    public void process2(String message){
        log.info("StreamReceiver2: {}",message);
    }
}
