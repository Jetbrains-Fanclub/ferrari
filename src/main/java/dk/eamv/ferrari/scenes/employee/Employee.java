package dk.eamv.ferrari.scenes.employee;

import java.util.ArrayList;

// Made by: Mikkel
public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private double maxLoan;

    public Employee(
        int id,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String password,
        double maxLoan
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.maxLoan = maxLoan;
    }

    //Overloaded to account for CREATEs with no ID
    public Employee(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String password,
        double maxLoan
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.maxLoan = maxLoan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMaxLoan() {
        return maxLoan;
    }

    public void setMaxLoan(double maxLoan) {
        this.maxLoan = maxLoan;
    }

    public boolean isSalesManager() {
        return maxLoan == -1;
    }

    public ArrayList<String> getProperties() {
        var properties = new ArrayList<String>();
        properties.add(password);
        properties.add(email);
        properties.add(String.valueOf(maxLoan));
        properties.add(phoneNumber);
        properties.add(lastName);
        properties.add(firstName);
        return properties;
    }

    @Override
    public String toString() {
        return String.format("%-4d %s %s", id, firstName, lastName);
    }
}
