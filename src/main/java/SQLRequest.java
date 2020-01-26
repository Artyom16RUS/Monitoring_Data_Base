
import java.sql.*;

public class SQLRequest {
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private String sqlCommand;

    public SQLRequest()  {

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users(\n" +
                    "                      id INTEGER PRIMARY KEY,\n" +
                    "                      name TEXT NOT NULL,\n" +
                    "                      age INTEGER NOT NULL,\n" +
                    "                      address TEXT NOT NULL\n" +
                    ")");
            statement.execute("CREATE TABLE IF NOT EXISTS user_log (\n" +
                    "                          new_id INTEGER,\n" +
                    "                          new_name TEXT NOT NULL,\n" +
                    "                          new_age INTEGER NOT NULL,\n" +
                    "                          new_address TEXT NOT NULL,\n" +
                    "                          date TEXT NOT NULL,\n" +
                    "                          operation TEXT NOT NULL\n" +
                    ")");
            ServiceTrigger serviceTrigger = new ServiceTrigger();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insert(String name, int age, String address) {
        sqlCommand = "INSERT INTO users (name, age, address) VALUES (?, ?, ?)";
        int rows = 0;
        try {
            preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, address);
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;

    }

    public int update(int id, String name, int age, String address){
        sqlCommand = "UPDATE users SET name = ?, age = ?, address = ? WHERE id = ?";
        int rows = 0;
        try {
            preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, address);
            preparedStatement.setInt(4, id);
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public int delete(int id) {
        sqlCommand = "DELETE FROM users WHERE id = ?";
        int rows = 0;
        try {
            preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setInt(1, id);
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }


}
