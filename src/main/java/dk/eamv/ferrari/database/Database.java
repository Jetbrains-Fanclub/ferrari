package dk.eamv.ferrari.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

// Made by: Benjamin
public abstract class Database {

    private static Connection connection;

    /**
     * Initialize connection to the database using the filepath
     * @param connectionString the SQL Server connection URL
     */
    public static void init(String filepath) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + filepath);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Get the current database connection
     * @return the active connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Execute a query, crash on failure
     * @param query database query string
     * @return SQL.Statement.execute() boolean result
     */
    public static boolean execute(String query) {
        try {
            return getConnection().createStatement().execute(query);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }

    /**
     * Run a query, crash on failure
     * @param query database query string
     * @return SQL.ResultSet
     */
    public static ResultSet query(String query) {
        try {
            return getConnection().createStatement().executeQuery(query);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
