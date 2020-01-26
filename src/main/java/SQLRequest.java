import javax.naming.NamingException;
import java.sql.*;

public class SQLRequest {
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private String sqlCommand;
    private ServiceTrigger serviceTrigger;

    public SQLRequest() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
        statement = connection.createStatement();
        serviceTrigger = new ServiceTrigger(connection, statement);

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

    }

//    public List<Auto> getAll() throws SQLException {
//        try (var conn = ds.getConnection()) {
//            try (var stmt = conn.createStatement()) {
//                try (var rs = stmt.executeQuery("SELECT id, name, description, image FROM autos")) {
//                    var list = new ArrayList<Auto>();
//
//                    while (rs.next()) {
//                        list.add(new Auto(
//                                rs.getString("id"),
//                                rs.getString("name"),
//                                rs.getString("description"),
//                                rs.getString("image")
//                        ));
//                    }
//                    return list;
//                }
//            }
//        }
//    }

    public int insert(String name, int age, String address) throws SQLException {
        sqlCommand = "INSERT INTO users (name, age, address) VALUES (?, ?, ?)";
        preparedStatement = connection.prepareStatement(sqlCommand);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.setString(3, address);
        int rows = preparedStatement.executeUpdate();
        return rows;

    }

    public int update(int id, String name, int age, String address) throws SQLException {
        sqlCommand = "UPDATE users SET name = ?, age = ?, address = ? WHERE id = ?";
        preparedStatement = connection.prepareStatement(sqlCommand);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.setString(3, address);
        preparedStatement.setInt(4, id);
        int rows = preparedStatement.executeUpdate();
        return rows;
    }

    public int delete(int id) throws SQLException {
        sqlCommand = "DELETE FROM users WHERE id = ?";
        preparedStatement = connection.prepareStatement(sqlCommand);
        preparedStatement.setInt(1, id);
        int rows = preparedStatement.executeUpdate();
        return rows;
    }
}
