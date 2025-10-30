package org.lunskra.messaging;


import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class MasterdataChangedEventDeserializer
        extends ObjectMapperDeserializer<MasterdataChangedEvent> {
    public MasterdataChangedEventDeserializer() {
        super(MasterdataChangedEvent.class);
    }
}
