package net.mguenther.reactive.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import net.mguenther.reactive.outbox.OutboxItem;

import java.util.UUID;

@ApplicationScoped
public class EmployeeService {

    private final ObjectMapper mapper;

    public EmployeeService() {
        final PolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder().build();
        mapper = new ObjectMapper();
        mapper.activateDefaultTyping(validator, ObjectMapper.DefaultTyping.NON_FINAL);
    }

    @WithTransaction
    public Uni<Boolean> deleteById(final String employeeId) {
        return Employee.deleteById(employeeId)
                .chain(deleted -> toOutboxItem(asDeletedEvent(employeeId)).persist().replaceWith(deleted));
    }

    @WithTransaction
    public Uni<Employee> accept(final CreateEmployeeCommand command) {
        return toEmployee(command)
                .flatMap(employee -> employee.persist().replaceWith(employee))
                .chain(e2 -> toOutboxItem(asCreatedEvent(e2)).persist().replaceWith(e2));
    }

    private Uni<Employee> toEmployee(final CreateEmployeeCommand command) {
        return Uni.createFrom().item(new Employee(
                generateEmployeeId(),
                command.getGivenName(),
                command.getLastName(),
                command.getEmail(),
                command.getDepartment()));
    }

    private OutboxItem toOutboxItem(final EmployeeEvent event) {
        try {
            final String eventPayload = mapper.writeValueAsString(event);
            return new OutboxItem(eventPayload, "employee-event");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private EmployeeCreatedEvent asCreatedEvent(final Employee employee) {
        return new EmployeeCreatedEvent(
                employee.employeeId,
                employee.givenName,
                employee.lastName,
                employee.email,
                employee.departmentId);
    }

    private EmployeeDeletedEvent asDeletedEvent(final String employeeId) {
        return new EmployeeDeletedEvent(employeeId);
    }

    private static String generateEmployeeId() {
        return UUID.randomUUID().toString();
    }
}
