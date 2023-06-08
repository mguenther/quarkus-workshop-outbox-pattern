package workshop.quarkus.reactive.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeDeletedEvent extends EmployeeEvent {

    private final String employeeId;

    @JsonCreator
    public EmployeeDeletedEvent(@JsonProperty("employeeId") final String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
