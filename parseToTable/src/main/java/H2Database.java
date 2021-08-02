import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Database {
  static final String JDBC_DRIVER = "org.h2.Driver";
  static final String DB_URL = "jdbc:h2:mem:";

  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    executeQuery(createRestaurantTableSql(), getConnection());
  }

  private static String createRestaurantTableSql() {
    return "CREATE TABLE   RESTAURANTS " +
            "(id INTEGER not NULL, " +
            " name VARCHAR(255), " +
            " last VARCHAR(255), " +
            " age INTEGER, " +
            " PRIMARY KEY ( id ))";
  }

  private static void executeQuery(String sql, Connection conn) {
    Statement stmt = null;
    try {
      // Execute a query
      System.out.println("Creating table in given database...");
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      System.out.println("Created table in given database...");

      // Clean-up environment
      stmt.close();
      conn.close();
    } catch (SQLException se) {
      //Handle errors for JDBC
      se.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      //finally block used to close resources
      try {
        if (stmt != null) stmt.close();
      } catch (SQLException se2) {
      } // nothing we can do
      try {
        if (conn != null) conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      } //end finally try
    } //end try
  }

  private static Connection getConnection() throws ClassNotFoundException, SQLException {
    Connection conn = null;
    // STEP 1: Register JDBC driver
    Class.forName(JDBC_DRIVER);

    //STEP 2: Open a connection
    System.out.println("Connecting to database...");
    conn = DriverManager.getConnection(DB_URL);
    return conn;
  }
}
