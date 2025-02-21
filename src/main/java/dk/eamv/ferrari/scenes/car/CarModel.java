package dk.eamv.ferrari.scenes.car;

import dk.eamv.ferrari.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Made by: Benjamin
public final class CarModel {

    // Private constructor to disallow instantiation
    private CarModel() {}

    /**
     * Read a car from the database, based on the id.
     * @param car the id of the car to get from the database
     */
    public static void create(Car car) {
        try {
            var statement = Database.getConnection()
                .prepareStatement(
                    String.format(
                        """
                            INSERT INTO Car
                            VALUES (?, ?, ?);
                        """
                    )
                );

            statement.setString(1, car.getModel());
            statement.setInt(2, car.getYear());
            statement.setDouble(3, car.getPrice());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static Car read(int id) {
        var rs = Database.query("SELECT * FROM Car WHERE id = " + id);

        try {
            if (rs.next()) {
                return new Car(id, rs.getString("model"), rs.getInt("year"), rs.getDouble("price"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Get all cars from the database.
     * @return ArrayList of all the cars in the database
     */
    public static ArrayList<Car> readAll() {
        var cars = new ArrayList<Car>();

        try (ResultSet rs = Database.query("SELECT * FROM Car")) {
            while (rs.next()) {
                cars.add(new Car(rs.getInt("id"), rs.getString("model"), rs.getInt("year"), rs.getDouble("price")));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return cars;
    }

    /**
     * Update a car in the database based on the id.
     * @param car the car information to update with
     */
    public static void update(Car car) {
        try {
            var statement = Database.getConnection()
                .prepareStatement(
                    """
                        UPDATE Car
                        SET model = ?, year = ?, price = ?
                        WHERE id = ?;
                    """
                );

            statement.setString(1, car.getModel());
            statement.setInt(2, car.getYear());
            statement.setDouble(3, car.getPrice());
            statement.setInt(4, car.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Delete car from the database based on the id.
     * @param id the id of the car to delete from the database
     * @return boolean indicating if the deletion was successful
     */
    public static boolean delete(int id) {
        return Database.execute("DELETE FROM Car WHERE id = " + id);
    }
}
