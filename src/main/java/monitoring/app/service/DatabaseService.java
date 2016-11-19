package monitoring.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import monitoring.app.dao.JdbcDao;

@Service
public class DatabaseService {

    @Autowired
    protected JdbcDao jdbcDao;

    public boolean ping() {
        try {
            return jdbcDao.ping();}
        catch (Exception e) {
            return false;
        }
    }
}
