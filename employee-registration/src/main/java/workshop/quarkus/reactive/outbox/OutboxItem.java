package workshop.quarkus.reactive.outbox;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class OutboxItem extends PanacheEntityBase {

    @Id
    public String eventId;

    public LocalDateTime eventCreated;

    @Column(length = 10000)
    public String eventPayload;

    public String eventType;

    public Boolean published;

    public OutboxItem() {
    }

    public OutboxItem(final String eventPayload, final String eventType) {
        this.eventId = UUID.randomUUID().toString();
        this.eventCreated = LocalDateTime.now(Clock.systemUTC());
        this.eventPayload = eventPayload;
        this.eventType = eventType;
        this.published = false;
    }

    public static Uni<List<OutboxItem>> findUnpublished() {
        return list("published", Boolean.FALSE);
    }

    public static Uni<OutboxItem> findAnyUnpublished() {
        return find("published", Boolean.FALSE).singleResult();
    }
}
