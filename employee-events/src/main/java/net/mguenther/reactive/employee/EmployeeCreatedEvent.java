package net.mguenther.reactive.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeCreatedEvent extends EmployeeEvent {

    private final String employeeId;

    private final String givenName;

    private final String lastName;

    private final String email;

    private final String departmentName;

    private final String departmentDescription;

    private final String company;

    @JsonCreator
    public EmployeeCreatedEvent(@JsonProperty("employeeId") final String employeeId,
                                @JsonProperty("givenName") final String givenName,
                                @JsonProperty("lastName") final String lastName,
                                @JsonProperty("email") final String email,
                                @JsonProperty("departmentName") final String departmentName,
                                @JsonProperty("departmentDescription") final String departmentDescription,
                                @JsonProperty("company") final String company) {
        this.employeeId = employeeId;
        this.givenName = givenName;
        this.lastName = lastName;
        this.email = email;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
        this.company = company;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public String getCompany() {
        return company;
    }
}
