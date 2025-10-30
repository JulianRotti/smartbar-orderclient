package org.lunskra.messaging;

import io.quarkus.arc.Unremovable;
import io.quarkus.cache.CacheInvalidateAll;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
@Unremovable
public class MasterdataChangedEventListener {

    @Incoming("menu-update-events")
    @CacheInvalidateAll(cacheName = "menu-cache")
    public Uni<Void> onMasterdataChanged(final Message<MasterdataChangedEvent> message) {
        System.out.println("MasterdataChangedEventListener onMasterdataChanged");
        System.out.println(message.getPayload());
        return Uni.createFrom().completionStage(message.ack());
    }
}
