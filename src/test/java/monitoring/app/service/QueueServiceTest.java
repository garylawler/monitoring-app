package monitoring.app.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSRuntimeException;
import javax.jms.Queue;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class QueueServiceTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private Queue queue;

    @InjectMocks
    private QueueService queueService;

    @Test
    public void daoResultReturned() throws Exception {
        doNothing().when(jmsTemplate).send(eq(queue), isA(MessageCreator.class));
        assertThat(queueService.ping(), is(true));
    }

    @Test
    public void exceptionHandled() throws Exception {
        doThrow(new JMSRuntimeException("dummy", "dummy")).when(jmsTemplate).send(eq(queue), isA(MessageCreator.class));
        assertThat(queueService.ping(), is(false));
    }
}
