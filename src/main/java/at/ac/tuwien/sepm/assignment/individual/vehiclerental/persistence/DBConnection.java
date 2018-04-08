package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * used for creating and closing connections to the database
 */

public class DBConnection {

    private static Connection connection = null;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static boolean testmode = false;

    private DBConnection() {
    }

    /**
     * creates new connection if none exists
     * @return current connection
     */
    public static Connection getConnection() {
        if (connection == null) {
            connection = newConnection();
        }
        return connection;
    }

    /**
     * creates new connection
     * @return created connection
     */
    private static Connection newConnection() {
        Connection con = null;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            LOG.error("Class.forName failed",e);
        }
        if (testmode) {
            try {
                con = DriverManager.getConnection("jdbc:h2:mem:db1;" +
                        "IGNORECASE=TRUE;" +
                        "INIT=runscript from 'classpath:/database/createAndInsert.sql'",
                    "", "");
                LOG.info("Connection to database found");
            } catch (SQLException e) {
                LOG.error("Connection to database not found",e);


            }
        } else {
            try {
                con = DriverManager.getConnection("jdbc:h2:file:~/.sepm/database/sepm;" +
                        "IGNORECASE=TRUE;" +
                        "INIT=runscript from 'classpath:/database/createAndInsert.sql'" +
                        ";DB_CLOSE_ON_EXIT=FALSE;FILE_LOCK=NO",
                    "", "");
                LOG.info("Connection to database found");
            } catch (SQLException e) {
                LOG.error("Connection to database not found",e);

            }
        }

        return con;
    }


    /**
     * closes current connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                LOG.info("Connection to database closed");
            } catch (SQLException e) {
                LOG.error("Closing connection failed",e);
            }
        }
    }


    public static boolean isTestmode() {
        return testmode;
    }

    public static void setTestmode(boolean testmode) {
        DBConnection.testmode = testmode;
    }
}
