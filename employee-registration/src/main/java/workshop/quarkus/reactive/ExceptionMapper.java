package workshop.quarkus.reactive;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.core.Response;
import workshop.quarkus.reactive.employee.EmployeeNotFoundException;

import java.util.Map;

@ApplicationScoped
public class ExceptionMapper {

    private static final Map<Class<? extends Exception>, Response.Status> EXCEPTION_TO_STATUS_CODE = Map.of(
            EmployeeNotFoundException.class, Response.Status.NOT_FOUND,
            IllegalStateException.class, Response.Status.CONFLICT,
            PersistenceException.class, Response.Status.CONFLICT
    );

    public Response handle(final Throwable cause) {
        final var status = EXCEPTION_TO_STATUS_CODE.getOrDefault(cause.getClass(), Response.Status.INTERNAL_SERVER_ERROR);
        return Response.status(status).build();
    }
}
