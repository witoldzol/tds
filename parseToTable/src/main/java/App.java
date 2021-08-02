import java.sql.SQLException;

public class App {
  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    H2Database.executeQuery(H2Database.createRestaurantTableSql(), H2Database.getConnection());
  }
}
