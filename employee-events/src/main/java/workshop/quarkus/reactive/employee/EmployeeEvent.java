package workshop.quarkus.reactive.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmployeeCreatedEvent.class, name = "employee-created-event"),
        @JsonSubTypes.Type(value = EmployeeDeletedEvent.class, name = "employee-deleted-event")
})
abstract public class EmployeeEvent {

    /**
     * This is a unique identifier for the event throughout the system.
     */
    private final String eventId;

    /**
     * The event time is a timestamp in milliseconds since epoch (UTC).
     */
    private final long eventTime;

    public EmployeeEvent() {
        this(generateEventId(), now());
    }

    @JsonCreator
    public EmployeeEvent(@JsonProperty("eventId") final String eventId,
                         @JsonProperty("eventTime") final long eventTime) {
        this.eventId = eventId;
        this.eventTime = eventTime;
    }

    public String getEventId() {
        return eventId;
    }

    public long getEventTime() {
        return eventTime;
    }

    private static String generateEventId() {
        return UUID.randomUUID().toString();
    }

    private static long now() {
        return Instant.now(Clock.systemUTC()).toEpochMilli();
    }
}