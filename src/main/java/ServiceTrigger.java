import util.JdbcTemplate;

import java.sql.*;
import java.util.List;

public class ServiceTrigger {

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public ServiceTrigger(Connection connection, Statement statement, PreparedStatement preparedStatement) throws SQLException {
        this.connection = connection;
        this.statement = statement;
        this.preparedStatement = preparedStatement;

        statement.execute("CREATE TRIGGER IF NOT EXISTS after_insert AFTER INSERT\n" +
                "    ON users\n" +
                "BEGIN\n" +
                "    INSERT INTO user_log(new_id, new_name, new_age, new_address, date, operation)\n" +
                "    VALUES (NEW.id, NEW.name, NEW.age, NEW.address, datetime('now'), 'ins');\n" +
                "END;");
        statement.execute("CREATE TRIGGER IF NOT EXISTS after_update AFTER UPDATE\n" +
                "    ON users\n" +
                "BEGIN\n" +
                "    INSERT INTO user_log(new_id, new_name, new_age, new_address, date, operation)\n" +
                "    VALUES (OLD.id, OLD.name, OLD.age, OLD.address, datetime('now'), 'upd');\n" +
                "END;");
        statement.execute("CREATE TRIGGER IF NOT EXISTS after_delete AFTER DELETE\n" +
                "    ON users\n" +
                "BEGIN\n" +
                "    INSERT INTO user_log(new_id, new_name, new_age, new_address, date, operation)\n" +
                "    VALUES (OLD.id, OLD.name, OLD.age, OLD.address, datetime('now'), 'del');\n" +
                "END;");
        connection.close();
    }

//    public List<User> getChanged(int day, int month, int year) throws SQLException {
//        String time = day + "-" + month + "-" + year;
//        preparedStatement = connection.prepareStatement("SELECT id, name, age, address FROM user_log WHERE date LIKE ?");
//        preparedStatement.setString(1, "'" + time +"%'");
//        int rows = preparedStatement.executeUpdate();
//        connection.close();
//        return null;
//    }

    public List<User> getChanged(int day, int month, int year) throws SQLException {
        String time = day + "-" + month + "-" + year;
        return JdbcTemplate.executeQuery(
                connection,
                "SELECT id, name, age, address FROM user_log WHERE date LIKE '" + time + "%'",
                resultSet -> new User(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getString("address")
                )
        );
    }
}
