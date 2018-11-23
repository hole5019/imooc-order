package com.imooc.order.message;

import com.imooc.order.ImoocOrderApplicationTests;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * 发送MQ消息测试
 * Created by helei on 2018-11-21.
 */
@Component
public class MqReceiverTest extends ImoocOrderApplicationTests{

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void process() throws Exception {
        amqpTemplate.convertAndSend("myQueue","now " + new Date());
    }

    @Test
    public void sendOrder() throws Exception {
        amqpTemplate.convertAndSend("myOrder","computer","now " + new Date());
    }

}