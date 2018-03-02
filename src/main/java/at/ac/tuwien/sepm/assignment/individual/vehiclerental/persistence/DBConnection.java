package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection = null;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    private DBConnection(){};

    private static boolean testMode = false;

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
            con = DriverManager.getConnection("jdbc:h2:file:~/sepm;" +
                    "IGNORECASE=TRUE;" +
                    "INIT=runscript from 'classpath:/database/createAndInsert.sql'",
                "sa", "");
            LOG.info("Connection to database found");
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("Connection to database not found");
        }

        return con;
    }

}
