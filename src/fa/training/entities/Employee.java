package fa.training.entities;

public class Employee {
    private int employeeId;
    private String employeeName;
    private double salary;
    private int spvrId;

    public Employee() {
    }

    public Employee(int employeeId, String employeeName, double salary, int spvrId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.salary = salary;
        this.spvrId = spvrId;
    }
}
