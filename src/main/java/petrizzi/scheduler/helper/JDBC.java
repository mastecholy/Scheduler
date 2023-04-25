package petrizzi.scheduler.helper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class JDBC {

    private static final String protocol = "jdbc:";
    private static final String vendor = "mysql:";
    private static final String location = "//localhost/";
    private static final String database = "client_schedule";

    private static final String jdbcURL = protocol + vendor + location + database + "?connectionTimeZone = SERVER";

    private static final String driver = "com.mysql.cj.jdbc.Driver";

    /*private static final String userName = "sqlUser";
    private static final String userPass = "Passw0rd!";*/

    public static Connection connection;


    public static void openConnection(String userName, String userPass) {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcURL, userName, userPass);
            System.out.println("Connection open.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeConnection(){
        try{
            connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


}
