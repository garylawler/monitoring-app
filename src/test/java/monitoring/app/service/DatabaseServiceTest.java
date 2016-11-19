package monitoring.app.service;

import org.hibernate.exception.JDBCConnectionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import monitoring.app.dao.JdbcDao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseServiceTest {

    @Mock
    private JdbcDao jdbcDao;

    @InjectMocks
    private DatabaseService databaseService;

    @Test
    public void daoResultReturned() throws Exception {
        when(jdbcDao.ping()).thenReturn(true);
        assertThat(databaseService.ping(), is(true));
    }

    @Test
    public void exceptionHandled() throws Exception {
        when(jdbcDao.ping()).thenThrow(new JDBCConnectionException("dummy", null));
        assertThat(databaseService.ping(), is(false));
    }
}
