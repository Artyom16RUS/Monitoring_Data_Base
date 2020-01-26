import java.sql.*;

public class ServiceTrigger {

    private Connection connection;
    private Statement statement;

    public ServiceTrigger(Connection connection, Statement statement) throws SQLException {
        this.connection = connection;
        this.statement = statement;

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
    }
}
