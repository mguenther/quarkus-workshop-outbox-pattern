package workshop.quarkus.reactive.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeCreatedEvent extends EmployeeEvent {

    private final String employeeId;

    private final String givenName;

    private final String lastName;

    private final String email;

    private final String departmentName;

    @JsonCreator
    public EmployeeCreatedEvent(@JsonProperty("employeeId") final String employeeId,
                                @JsonProperty("givenName") final String givenName,
                                @JsonProperty("lastName") final String lastName,
                                @JsonProperty("email") final String email,
                                @JsonProperty("departmentName") final String departmentName) {
        this.employeeId = employeeId;
        this.givenName = givenName;
        this.lastName = lastName;
        this.email = email;
        this.departmentName = departmentName;
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
}
