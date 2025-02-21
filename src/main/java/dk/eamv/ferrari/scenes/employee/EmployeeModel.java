package dk.eamv.ferrari.scenes.employee;

import dk.eamv.ferrari.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Made by: Benjamin
public final class EmployeeModel {

    // Private constructor to disallow instantiation
    private EmployeeModel() {}

    /**
     * Creates a new employee in the database.
     * @param employee an Employee object containing all the employee data
     */
    public static void create(Employee employee) {
        try {
            var statement = Database.getConnection()
                .prepareStatement(
                    String.format(
                        """
                            INSERT INTO Employee
                            VALUES (?, ?, ?, ?, ?, ?);
                        """
                    )
                );

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getPhoneNumber());
            statement.setString(4, employee.getEmail());
            statement.setString(5, employee.getPassword());
            statement.setDouble(6, employee.getMaxLoan());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Reads an Employee from the database based on an id.
     * @param id the id to read
     * @return an Employee containing all the row data
     */
    public static Employee read(int id) {
        var rs = Database.query("SELECT * FROM Employee WHERE id = " + id);

        try {
            if (rs.next()) {
                return new Employee(
                    id,
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getDouble("max_loan")
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Get all the employees from the database.
     * @return an ArrayList of Employees
     */
    public static ArrayList<Employee> readAll() {
        var employees = new ArrayList<Employee>();

        try (var rs = Database.query("SELECT * FROM Employee")) {
            while (rs.next()) {
                employees.add(
                    new Employee(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getDouble("max_loan")
                    )
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return employees;
    }

    public static ArrayList<Employee> readAllSellers() {
        var employees = readAll();
        employees.removeIf(employee -> employee.isSalesManager());
        return employees;
    }

    /**
     * Get a specific amount of Employees from a "page".
     * @param page the page to read
     * @param amount the amount to read (e.g. 20 employees)
     * @return an ArrayList of Employees
     */
    public static ArrayList<Employee> readPage(int page, int amount) {
        var offset = page * amount;
        var rs = Database.query(
            String.format(
                """
                    SELECT * FROM Employee
                    ORDER BY id
                    OFFSET %d ROWS
                    FETCH NEXT %d ROWS ONLY;
                """,
                offset,
                amount
            )
        );

        var employees = new ArrayList<Employee>();
        try {
            while (rs.next()) {
                employees.add(
                    new Employee(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getDouble("max_loan")
                    )
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return employees;
    }

    /**
     * Update an employee in the database based on the id.
     * @param employee the employee data to update with
     */
    public static void update(Employee employee) {
        try {
            var statement = Database.getConnection()
                .prepareStatement(
                    """
                        UPDATE Employee
                        SET
                            first_name = ?, last_name = ?, phone_number = ?,
                            email = ?, password = ?, max_loan = ?
                        WHERE id = ?;
                    """
                );

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getPhoneNumber());
            statement.setString(4, employee.getEmail());
            statement.setString(5, employee.getPassword());
            statement.setDouble(6, employee.getMaxLoan());
            statement.setInt(7, employee.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Delete an employee from the database based on the id.
     * @param id the id to delete from the database
     * @return boolean to show if the deletion was successful
     */
    public static boolean delete(int id) {
        return Database.execute("DELETE FROM Employee WHERE id = " + id);
    }
}
