package org.lunskra.messaging;

import io.quarkus.arc.Unremovable;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import org.lunskra.smartbar.orderclient.model.OrderItem;

import java.time.ZonedDateTime;
import java.util.List;

@ApplicationScoped
@Unremovable
public class OrderProcessedMessagingService {

    private final MutinyEmitter<List<OrderItem>> emitter;

    @Inject
    public OrderProcessedMessagingService(@Channel("order-processed-events") MutinyEmitter<List<OrderItem>> emitter) {
        this.emitter = emitter;
    }

    public Uni<Void> fireOrderProcessedEvent(List<OrderItem> orderItems) {
        final var metadata = new OutgoingRabbitMQMetadata().builder()
                .withRoutingKey("processed-orders")
                .withTimestamp(ZonedDateTime.now())
                .build();

        return this.emitter.sendMessage(Message.of(orderItems, Metadata.of(metadata)));
    }
}
