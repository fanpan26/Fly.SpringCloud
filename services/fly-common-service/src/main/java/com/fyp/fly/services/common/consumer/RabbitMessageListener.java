package com.fyp.fly.services.common.consumer;

import com.fyp.fly.common.event.CountEvent;
import com.fyp.fly.common.utils.JSONUtils;
import com.fyp.fly.services.common.service.CountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMessageListener implements MessageListener {

    @Autowired
    private CountService countService;

    private static final Logger logger = LoggerFactory.getLogger(RabbitMessageListener.class);

    @Override
    public void onMessage(Message message) {
        try {
            CountEvent event = JSONUtils.parseObject(message.getBody(), CountEvent.class);
            System.out.println(message.getBody());
            if (event.isIncrement()) {
                countService.increment(event.getBizType(), event.getBizId());
            } else {
                countService.decrement(event.getBizType(), event.getBizId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void containerAckMode(AcknowledgeMode mode) {

    }
}
