package net.mguenther.reactive.employee;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employee extends PanacheEntityBase {

    @Id
    public String employeeId;

    public String givenName;

    public String lastName;

    public String email;

    public String departmentId;

    public Employee() {
    }

    public Employee(final String employeeId, final String givenName, final String lastName, final String email, final String departmentId) {
        this.employeeId = employeeId;
        this.givenName = givenName;
        this.lastName = lastName;
        this.email = email;
        this.departmentId = departmentId;
    }
}
