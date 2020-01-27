package util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JdbcTemplate {
    private JdbcTemplate() {
    }

    public static <T> List<T> executeQuery(Statement statement, String sql, RowMapper<T> mapper) throws SQLException {
        try (
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            List<T> result = new LinkedList<>();
            while (resultSet.next()) {
                result.add(mapper.map(resultSet));
            }
            return result;
        }
    }
}
