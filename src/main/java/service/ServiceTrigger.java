package service;

import model.User;
import util.JdbcTemplate;

import java.sql.*;
import java.util.List;

public class ServiceTrigger{

    private Connection connection;
    private Statement statement;

    /**
     * Constructor - creates a trigger in a database based on an existing table.
     *
     *  @version 1.0
     *  @autor Shaykhutdinov Artyom
     */
    public ServiceTrigger(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS user_log (" +
                    "                          new_id INTEGER," +
                    "                          new_name TEXT NOT NULL," +
                    "                          new_age INTEGER NOT NULL," +
                    "                          new_address TEXT NOT NULL," +
                    "                          date TEXT NOT NULL," +
                    "                          operation TEXT NOT NULL" +
                    ")");
            statement.execute("CREATE TRIGGER IF NOT EXISTS after_insert AFTER INSERT ON users " +
                    "BEGIN" +
                    " INSERT INTO user_log(new_id, new_name, new_age, new_address, date, operation)" +
                    "    VALUES (NEW.id, NEW.name, NEW.age, NEW.address, datetime('now'), 'ins');" +
                    "END;");
            statement.execute("CREATE TRIGGER IF NOT EXISTS after_update AFTER UPDATE ON users " +
                    "BEGIN" +
                    "    INSERT INTO user_log(new_id, new_name, new_age, new_address, date, operation)" +
                    "    VALUES (OLD.id, OLD.name, OLD.age, OLD.address, datetime('now'), 'upd');" +
                    "END;");
            statement.execute("CREATE TRIGGER IF NOT EXISTS after_delete AFTER DELETE ON users " +
                    "BEGIN" +
                    "    INSERT INTO user_log(new_id, new_name, new_age, new_address, date, operation)" +
                    "    VALUES (OLD.id, OLD.name, OLD.age, OLD.address, datetime('now'), 'del');" +
                    "END;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * List of changes.
     *
     * @param time - time for the period of which there were changes.
     * @return - list of changes for the specified time.
     */
    public List<User> getDataChanges(String time){
        List<User> list = null;
        try {
            list = JdbcTemplate.executeQuery(
                    statement,
                    "SELECT new_name, new_age, new_address, date FROM user_log WHERE date LIKE '" + time + "%'",
                    resultSet -> new User(
                            resultSet.getString("new_name"),
                            resultSet.getInt("new_age"),
                            resultSet.getString("new_address"),
                            resultSet.getString("date")
                    )
            );
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
