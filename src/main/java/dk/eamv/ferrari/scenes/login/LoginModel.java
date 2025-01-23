package dk.eamv.ferrari.scenes.login;

import dk.eamv.ferrari.database.Database;
import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.employee.EmployeeModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Made by: Christian
// Checked by: Benjamin
public class LoginModel {

    public static Employee authenticate(String email, String password) {
        try {
            var statement = Database.getConnection()
                .prepareStatement("SELECT id FROM Employee WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);

            var rs = statement.executeQuery();
            if (rs.next()) {
                return EmployeeModel.read(rs.getInt("id"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
