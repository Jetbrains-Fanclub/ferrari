package dk.eamv.ferrari.scenes.customer;

import dk.eamv.ferrari.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Made by: Benjamin
public final class CustomerModel {

    // Private constructor to disallow instantiation
    private CustomerModel() {}

    /**
     * Creates a new customer in the database.
     * @param customer an Customer object containing all the customer data
     */
    public static void create(Customer customer) {
        try {
            var statement = Database.getConnection()
                .prepareStatement(
                    String.format(
                        """
                            INSERT INTO Customer
                            VALUES (?, ?, ?, ?, ?, ?);
                        """
                    )
                );

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getAddress());
            statement.setString(6, customer.getCpr());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Read a customer from the database, based on the id.
     * @param id the id of the customer to get from the database
     * @return Customer containing the database row information
     */
    public static Customer read(int id) {
        var rs = Database.query("SELECT * FROM Customer WHERE id = " + id);

        try {
            if (rs.next()) {
                return new Customer(
                    id,
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("cpr")
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Get all customers from the database.
     * @return ArrayList of all the Customers in the database
     */
    public static ArrayList<Customer> readAll() {
        var customers = new ArrayList<Customer>();

        try (var rs = Database.query("SELECT * FROM Customer")) {
            while (rs.next()) {
                customers.add(
                    new Customer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("cpr")
                    )
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return customers;
    }

    /**
     * Update a customer in the database based on the id.
     * @param customer the customer information to update with
     */
    public static void update(Customer customer) {
        try {
            var statement = Database.getConnection()
                .prepareStatement(
                    """
                        UPDATE Customer
                        SET
                            first_name = ?, last_name = ?,
                            phone_number = ?, email = ?,
                            address = ?, cpr = ?
                        WHERE id = ?;
                    """
                );

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getAddress());
            statement.setString(6, customer.getCpr());
            statement.setInt(7, customer.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Delete customer from the database based on the id.
     * @param id the id of the customer to delete from the database
     * @return boolean indicating if the deletion was successful
     */
    public static boolean delete(int id) {
        return Database.execute("DELETE FROM Customer WHERE id = " + id);
    }
}
