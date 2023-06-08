package workshop.quarkus.reactive.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class EmployeeEventDeserializer implements Deserializer<EmployeeEvent> {

    private final ObjectMapper mapper;

    public EmployeeEventDeserializer() {
        final PolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder().build();
        mapper = new ObjectMapper();
        mapper.activateDefaultTyping(validator, ObjectMapper.DefaultTyping.NON_FINAL);
    }

    @Override
    public EmployeeEvent deserialize(final String topic, final byte[] bytes) {
        try {
            return mapper.readValue(bytes, EmployeeEvent.class);
        } catch (Exception e) {
            throw new SerializationException("Unable to deserialize record payload into sub-type of the employee event protocol.", e);
        }
    }
}
