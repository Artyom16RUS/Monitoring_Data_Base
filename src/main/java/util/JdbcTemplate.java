package util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JdbcTemplate {
    private JdbcTemplate() {
    }

    public static <T> List<T> executeQuery(Connection connection, String sql, RowMapper<T> mapper) throws SQLException {
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            List<T> result = new LinkedList<>();
            while (resultSet.next()) { // переходит на следующую позицию и возвращает true, если там есть данные
                result.add(mapper.map(resultSet));
            }
            return result;
        }
    }
}
