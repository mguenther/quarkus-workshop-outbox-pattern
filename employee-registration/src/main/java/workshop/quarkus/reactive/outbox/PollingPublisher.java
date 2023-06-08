package workshop.quarkus.reactive.outbox;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;

@ApplicationScoped
public class PollingPublisher {

    private final MutinyEmitter<String> eventEmitter;

    @Inject
    public PollingPublisher(@Channel("employee-events") final MutinyEmitter<String> eventEmitter) {
        this.eventEmitter = eventEmitter;
    }

    @Scheduled(every = "10s")
    @WithSession
    @WithTransaction
    Uni<Void> publishOutboxItems() {
        return OutboxItem.findAnyUnpublished()
                .flatMap(outboxItem -> eventEmitter.send(outboxItem.eventPayload).replaceWith(outboxItem))
                .flatMap(outboxItem -> {
                    outboxItem.published = Boolean.TRUE;
                    return outboxItem.persist().replaceWith(outboxItem);
                })
                .replaceWithVoid();
    }
}
