package util;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> {
  T map(ResultSet resultSet) throws SQLException; // если в лямбде может быть сгенерировано Checked Exception, то и в сигнатуре должно быть
}
