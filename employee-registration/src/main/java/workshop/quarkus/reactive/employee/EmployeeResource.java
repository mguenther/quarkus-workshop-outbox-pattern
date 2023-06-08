package workshop.quarkus.reactive.employee;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import workshop.quarkus.reactive.ExceptionMapper;

import java.util.List;


@Path("/employees")
@ApplicationScoped
public class EmployeeResource {

    private final ExceptionMapper exceptionMapper;

    private final EmployeeService employeeService;

    @Inject
    public EmployeeResource(final ExceptionMapper exceptionMapper,
                            final EmployeeService employeeService) {
        this.exceptionMapper = exceptionMapper;
        this.employeeService = employeeService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Employee>> getEmployees() {
        return Employee.listAll();
    }

    @GET
    @Path("/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getEmployee(@PathParam("employeeId") final String employeeId) {
        return Employee.findById(employeeId)
                .onItem().ifNull().failWith(() -> new EmployeeNotFoundException(employeeId))
                .onItem().transform(employee -> Response.ok(employee).build())
                .onFailure().recoverWithItem(exceptionMapper::handle);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createEmployee(final CreateEmployeeCommand command) {
        return employeeService.accept(command)
                .map(employee -> Response.ok(employee).status(Response.Status.CREATED).build())
                .onFailure().recoverWithItem(exceptionMapper::handle);
    }

    @DELETE
    @Path("/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteEmployee(@PathParam("employeeId") final String employeeId) {
        return employeeService.deleteById(employeeId)
                .map(deleted -> deleted
                        ? Response.ok().status(Response.Status.NO_CONTENT).build()
                        : Response.ok().status(Response.Status.NOT_FOUND).build())
                .onFailure().recoverWithItem(exceptionMapper::handle);
    }
}
