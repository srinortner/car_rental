package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnection {

    private static Connection connection = null;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    private DBConnection(){};

    public static Connection getConnection() {
        if(connection == null) {
            connection = newConnection();
        }
        return connection;
    }

    private static Connection newConnection() {
        Connection con = null;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LOG.error("Class.forName failed");
        }
        try {
            con = DriverManager.getConnection("jdbc:h2:file:~/.sepm/database/sepm;" +
                    "IGNORECASE=TRUE;" +
                    "INIT=runscript from 'classpath:/database/createAndInsert.sql'",
                "", "");
            LOG.info("Connection to database found");
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("Connection to database not found");
        }

        return con;
    }

    public static void closeConnection(){
        if(connection != null) {
            try {
                connection.close();
                connection = null;
                LOG.info("Connection to database closed");
            } catch (SQLException e) {
                e.printStackTrace();
                LOG.error("Closing connection failed");
            }
        }
    }

}
