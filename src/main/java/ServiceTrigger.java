import util.JdbcTemplate;

import java.sql.*;
import java.util.List;

public class ServiceTrigger{

    private Connection connection;
    private Statement statement;

    public ServiceTrigger(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            statement = connection.createStatement();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getChanged(String date){
        List<User> list = null;
        try {
            list = JdbcTemplate.executeQuery(
                    connection,
                    "SELECT new_name, new_age, new_address, date FROM user_log WHERE date LIKE '" + date + "%'",
                    resultSet -> new User(
                            resultSet.getString("new_name"),
                            resultSet.getInt("new_age"),
                            resultSet.getString("new_address"),
                            resultSet.getString("date")
                    )
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
