import javax.naming.NamingException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, NamingException {
        SQLRequest service = new SQLRequest();
        service.insert("Mike", 18, "Moscow");
        service.insert("Musk", 22, "USA");
        service.insert("Ola", 33, "GB");
        service.update(1, "none", 0, "none");
        service.delete(2);


    }
}
