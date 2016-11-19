package monitoring.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcDao {

    protected static final String queryPattern = "SELECT 1 FROM DUAL";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public boolean ping() {
        return jdbcTemplate.queryForObject(queryPattern, new Object[]{}, new RowMapper<Boolean>() {
            @Override
            public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("1") != null;
            }
        });
    }
}
