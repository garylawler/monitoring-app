package monitoring.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import java.util.UUID;

@Component
public class QueueService {

    private Logger logger = LoggerFactory.getLogger(QueueService.class);
    private JmsTemplate jmsTemplate;
    private Queue sendQueue;

    @Autowired
    public QueueService(JmsTemplate jmsTemplate, @Qualifier("queue.heartbeat") Queue sendQueue) {
        this.jmsTemplate = jmsTemplate;
        this.sendQueue = sendQueue;
        jmsTemplate.setDefaultDestination(sendQueue);
    }

    public boolean ping() {
        try {
            jmsTemplate.send(sendQueue, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    Message message = session.createMessage();
                    message.setJMSCorrelationID(UUID.randomUUID().toString());
                    return message;
                }
            });
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
}
