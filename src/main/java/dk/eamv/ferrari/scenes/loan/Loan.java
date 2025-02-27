package dk.eamv.ferrari.scenes.loan;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.car.CarModel;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.customer.CustomerModel;
import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.employee.EmployeeModel;
import java.util.Date;

// Made by: Mikkel
public class Loan {

    private int id;
    private int car_id;
    private int customer_id;
    private int employee_id;
    private double loanSize;
    private double downPayment;
    private double interestRate;
    private Date startDate;
    private Date endDate;
    private LoanStatus status;

    public Loan(
        int id,
        int car_id,
        int customer_id,
        int employee_id,
        double loanSize,
        double downPayment,
        double interestRate,
        Date startDate,
        Date endDate,
        LoanStatus status
    ) {
        this.id = id;
        this.car_id = car_id;
        this.customer_id = customer_id;
        this.employee_id = employee_id;
        this.loanSize = loanSize;
        this.downPayment = downPayment;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Overloaded to allow for CREATE operations. ID is removed since its autogenerated on INSERT.
    public Loan(
        int car_id,
        int customer_id,
        int employee_id,
        double loanSize,
        double downPayment,
        double interestRate,
        Date startDate,
        Date endDate,
        LoanStatus status
    ) {
        this.car_id = car_id;
        this.customer_id = customer_id;
        this.employee_id = employee_id;
        this.loanSize = loanSize;
        this.downPayment = downPayment;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public double getLoanSize() {
        return loanSize;
    }

    public void setLoanSize(double loanSize) {
        this.loanSize = loanSize;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public String getCarLabel() {
        var car = CarModel.read(car_id);
        assert car != null;
        return car.getModel();
    }

    public String getCustomerLabel() {
        var customer = CustomerModel.read(customer_id);
        assert customer != null;
        return customer.getFirstName() + " " + customer.getLastName();
    }

    public String getEmployeeLabel() {
        var employee = EmployeeModel.read(employee_id);
        assert employee != null;
        return employee.getFirstName() + " " + employee.getLastName();
    }
}
