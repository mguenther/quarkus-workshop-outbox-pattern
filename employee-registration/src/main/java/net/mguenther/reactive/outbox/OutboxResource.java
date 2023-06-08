package net.mguenther.reactive.outbox;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/outbox")
@ApplicationScoped
public class OutboxResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<OutboxItem>> getOutgoingEvents() {
        return OutboxItem.listAll();
    }
}
