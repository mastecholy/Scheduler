package petrizzi.scheduler.helper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Abstract class for establishing and closing connections with the mysql database.
 */
public abstract class JDBC {

    private static final String protocol = "jdbc:";
    private static final String vendor = "mysql:";
    private static final String location = "//localhost/";
    private static final String database = "client_schedule";

    /**
     * String piecing together the url to establish the JDBC connection.
     */
    private static final String jdbcURL = protocol + vendor + location + database + "?connectionTimeZone = SERVER";

    private static final String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection connection;


    /**
     * Method for opening a JDBC connection with the mysql database.
     * @param userName username used to log into the database.
     * @param userPass password used to log into the database.
     */
    public static void openConnection(String userName, String userPass) {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcURL, userName, userPass);
            //System.out.println("Connection open.");
        } catch (ClassNotFoundException | SQLException ignored) {
        }
    }

    /**
     * Method for closing an established connection with the mysql database.
     */
    public static void closeConnection(){
        try{
            connection.close();
            //System.out.println("Connection closed.");
        } catch (SQLException ignored) {
        }

    }


}
