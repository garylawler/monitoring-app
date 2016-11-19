package monitoring.config.jms;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Queue;
import java.util.UUID;

@Configuration
@PropertySource("classpath:jms.properties")
public class JmsConfig {

    private Logger logger = LoggerFactory.getLogger(JmsConfig.class);

    @Autowired
    private Environment env;

    @Bean
    public ConnectionFactory getConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(env.getProperty("broker.url"));
        connectionFactory.setClientID(UUID.randomUUID().toString());
        connectionFactory.setSendTimeout(100);

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setTargetConnectionFactory(connectionFactory);

        return cachingConnectionFactory;
    }

    @Bean(name = "jmsTemplate")
    @Autowired
    public JmsTemplate getJmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setExplicitQosEnabled(true);
        jmsTemplate.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        jmsTemplate.setTimeToLive(1000L);

        return jmsTemplate;
    }

    @Bean(name = "queue.heartbeat")
    public Queue getAuthQueue() {
        return new ActiveMQQueue("queue.heartbeat");
    }
}
